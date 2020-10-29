// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.n7space.capellatasteplugin.utils.BasicConfigurableItem;

/**
 * Converts user-supplied element names into ones acceptable by the given formal notation. Provides facilities for name
 * collision resolution.
 *
 */
public class NameConverter extends BasicConfigurableItem {

  /**
   * Enumeration listing possible NameConverter option's handles.
   */
  public static enum NameConverterOption {
    /**
     * Force prefix for ASN.1 package names.
     */
    ForcePackagePrefix("ForcePackagePrefix"),
    /**
     * Add prefix to ASN.1 package names if necessary.
     */
    AddPackagePrefixIfNecessary("AddPackagePrefixIfNecessary"),
    /**
     * ASN.1 Package name prefix.
     */
    PackagePrefix("PackagePrefix"),
    /**
     * Force prefix for ASN.1 type names.
     */
    ForceAsn1TypePrefix("ForceAsn1TypePrefix"),
    /**
     * Add prefix to ASN.1 type names if necessary.
     */
    AddAsn1TypePrefixIfNecessary("AddAsn1TypePrefixIfNecessary"),
    /**
     * ASN.1 type name prefix.
     */
    Asn1TypePrefix("Asn1TypePrefix"),
    /**
     * Resolve ASN.1 type name conflicts.
     */
    ResolveAsn1TypeConflicts("ResolveAsn1TypeConflicts"),
    /**
     * Force prefix for ASN.1 identifier names.
     */
    ForceAsn1IdentifierPrefix("ForceAsn1IdentifierPrefix"),
    /**
     * Add prefix to ASN.1 identifier names if necessary.
     */
    AddAsn1IdentifierPrefixIfNecessary("AddAsn1IdentifierPrefixIfNecessary"),
    /**
     * ASN.1 identifier name prefix.
     */
    Asn1IdentifierPrefix("Asn1IdentifierPrefix"),
    /**
     * Resolve ASN.1 identifier name conflicts.
     */
    ResolveAsn1IdentifierConflicts("ResolveAsn1IdentifierConflicts");

    /**
     * Prefix for option handles.
     */
    public final static String NAME_COVERTER_PREFIX = "com.n7space.capellatasteplugin.modelling.NameCoverter.";

    private final String value;

    private NameConverterOption(final String baseName) {
      value = baseName;
    }

    /**
     * Returns string representation of the option handle.
     *
     * @return String representation of the option handle.
     */
    @Override
    public String toString() {
      return NAME_COVERTER_PREFIX + value;
    }
  }

  protected static class NameDictionary {
    protected final String DIVIDER = "%|%";

    protected final Map<String, Integer> collisionCounters = new HashMap<String, Integer>();
    protected final Map<String, String> translations = new HashMap<String, String>();

    /**
     * Removes all translations that refer to invalid (non-existent) IDs.
     *
     * @param validIds
     *          Valid (existing) IDs
     */
    public void cleanupTranslations(final HashSet<String> validIds) {
      final HashSet<String> handlesToRemove = new HashSet<String>();
      for (final String translationHandle : translations.keySet()) {
        final int divisionIndex = translationHandle.indexOf(DIVIDER);
        final String id = translationHandle.substring(divisionIndex + DIVIDER.length());
        if (!validIds.contains(id))
          handlesToRemove.add(translationHandle);
      }
      for (final String handle : handlesToRemove)
        translations.remove(handle);
    }

    /**
     * Clears all translations and collision counters.
     */
    public void clear() {
      collisionCounters.clear();
      translations.clear();
    }

    /**
     * Gets translation for the given name and ID.
     *
     * @param name
     *          Name to be translated
     * @param id
     *          ID of the element
     * @return Translated name
     */
    public String getTranslation(final String name, final String id) {
      final String translationHandle = name + DIVIDER + id;
      final String translation = translations.get(translationHandle);
      if (translation != null)
        return translation;
      final Integer collisionCounter = collisionCounters.get(name);
      if (collisionCounter == null) {
        collisionCounters.put(name, Integer.valueOf(0));
        translations.put(translationHandle, name);
        return name;
      }
      collisionCounters.put(name, collisionCounter + 1);
      final String newTranslation = name + (collisionCounter + 2);
      translations.put(translationHandle, newTranslation);
      return newTranslation;
    }

    /**
     * Recalculates name collision counters.
     */
    public void recalculateNameCollisions() {
      collisionCounters.clear();
      for (final String translationHandle : translations.keySet()) {
        final int divisionIndex = translationHandle.indexOf(DIVIDER);
        final String name = translationHandle.substring(0, divisionIndex);
        final Integer collisionCounter = collisionCounters.get(name);
        if (collisionCounter == null)
          collisionCounters.put(name, Integer.valueOf(0));
        else
          collisionCounters.put(name, Integer.valueOf(collisionCounter + 1));
      }
    }
  }

  protected static class NameDictionaryGroup {
    protected final Map<String, NameDictionary> dictionaries = new HashMap<String, NameDictionary>();

    /**
     * Cleans up the dictionary translations and recalculates name collisions.
     *
     * @param validIds
     *          Valid (existent) IDs.
     */
    public void cleanupTranslations(final HashSet<String> validIds) {
      for (final NameDictionary dictionary : dictionaries.values()) {
        dictionary.cleanupTranslations(validIds);
        dictionary.recalculateNameCollisions();
      }

    }

    /**
     * Clears the translation dictionaries.
     */
    public void clear() {
      dictionaries.clear();
    }

    /**
     * Gets translation for the given name within a group and with a given ID.
     *
     * @param group
     *          Name's group
     * @param name
     *          Name to be translated
     * @param id
     *          Name's ID
     * @return Translated name
     */
    public String getTranslation(final String group, final String name, final String id) {
      NameDictionary dictionary = dictionaries.get(group);
      if (dictionary == null) {
        dictionary = new NameDictionary();
        dictionaries.put(group, dictionary);
      }
      return dictionary.getTranslation(name, id);
    }
  }

  /**
   * List of identifiers reserved in ASN.1 syntax.
   */
  public final static String[] ASN_1_RESERVED_IDENTIFIERS = { "ABSENT", "ENCODED", "INTEGER", "RELATIVE-OID",
      "ABSTRACT-SYNTAX", "END", "INTERSECTION", "SEQUENCE", "ALL", "ENUMERATED", "ISO646String", "SET", "APPLICATION",
      "EXCEPT", "MAX", "SIZE", "AUTOMATIC", "EXPLICIT", "MIN", "STRING", "BEGIN", "EXPORTS", "MINUS-INFINITY", "SYNTAX",
      "BIT", "EXTENSIBILITY", "NULL", "T61String", "BMPString", "EXTERNAL", "NumericString", "TAGS", "BOOLEAN", "FALSE",
      "OBJECT", "TeletexString", "BY", "FROM", "ObjectDescriptor", "TRUE", "CHARACTER", "GeneralizedTime", "OCTET",
      "TYPE-IDENTIFIER", "CHOICE", "GeneralString", "OF", "UNION", "CLASS", "GraphicString", "OPTIONAL", "UNIQUE",
      "COMPONENT", "IA5String", "PATTERN", "UNIVERSAL", "COMPONENTS", "IDENTIFIER", "PDV", "UniversalString",
      "CONSTRAINED", "IMPLICIT", "PLUS-INFINITY", "UTCTime", "CONTAINING", "IMPLIED", "PRESENT", "UTF8String",
      "DEFAULT", "IMPORTS", "PrintableString", "VideotexString", "DEFINITIONS", "INCLUDES", "PRIVATE", "VisibleString",
      "EMBEDDED", "INSTANCE", "REAL", "WITH", "CHAR", "FLOAT", "DOUBLE", "LONG", "SHORT" };

  /**
   * List of identifiers reserved in AADL syntax. The list is not case sensitive.
   */
  public final static String[] AADL_RESERVED_IDENTIFIERS = { "aadlboolean", "aadlinteger", "aadlreal", "aadlstring",
      "abstract", "access", "all", "and", "annex", "list", "memory", "mode", "modes", "none", "not", "of", "or", "out",
      "package", "parameter", "applies", "binding", "bus", "calls", "classifier", "compute", "connections", "constant",
      "data", "path", "port", "private", "process", "processor", "properties", "property", "prototypes", "provides",
      "public", "delta", "device", "end", "enumeration", "event", "extends", "false", "feature", "features", "range",
      "record", "reference", "refined", "renames", "requires", "self", "set", "flow", "flows", "group",
      "implementation", "in", "inherit", "initial", "inverse", "is", "subcomponents", "subprogram", "system", "thread",
      "to", "true", "type", "units", "virtual", "with", };

  protected final Option[] OPTIONS = {
      new Option(NameConverterOption.ForcePackagePrefix, "Force prefix for package names", Boolean.FALSE),
      new Option(NameConverterOption.AddPackagePrefixIfNecessary, "Add prefix for package names if necessary",
          Boolean.FALSE),
      new Option(NameConverterOption.PackagePrefix, "Package prefix", "P"),
      new Option(NameConverterOption.ForceAsn1TypePrefix, "Force prefix for ASN.1 type names", Boolean.FALSE),
      new Option(NameConverterOption.AddAsn1TypePrefixIfNecessary, "Add prefix for ASN.1 type names if necessary",
          Boolean.TRUE),
      new Option(NameConverterOption.Asn1TypePrefix, "ASN.1 type name prefix", "T"),
      new Option(NameConverterOption.ResolveAsn1TypeConflicts, "Resolve ASN.1 type name conflicts via postfixes",
          Boolean.FALSE),
      new Option(NameConverterOption.ForceAsn1IdentifierPrefix, "Force prefix for ASN.1 identifier names",
          Boolean.FALSE),
      new Option(NameConverterOption.AddAsn1IdentifierPrefixIfNecessary,
          "Add prefix for ASN.1 identifier names if necessary", Boolean.TRUE),
      new Option(NameConverterOption.Asn1IdentifierPrefix, "ASN.1 identifier name prefix", "m"),
      new Option(NameConverterOption.ResolveAsn1IdentifierConflicts,
          "Resolve ASN.1 identifier name conflicts via postfixes", Boolean.FALSE), };

  protected final Set<String> reservedIdentifiers = new HashSet<String>();
  protected final NameDictionaryGroup dictionary = new NameDictionaryGroup();

  /**
   * The constructor.
   */
  public NameConverter() {
    for (final String identifier : ASN_1_RESERVED_IDENTIFIERS) {
      reservedIdentifiers.add(identifier.toLowerCase());
    }
    for (final String identifier : AADL_RESERVED_IDENTIFIERS) {
      reservedIdentifiers.add(identifier.toLowerCase());
    }
    setOptions(OPTIONS);
  }

  protected String capitalizeFirstLetter(final String source) {
    if (source.length() == 0)
      return source;
    return ("" + source.charAt(0)).toUpperCase() + source.substring(1);
  }

  /**
   * Cleans up translation based on the list of valid (existing) IDs.
   *
   * @param validIds
   *          List of valid IDs
   */
  public void cleanupTranslations(final HashSet<String> validIds) {
    dictionary.cleanupTranslations(validIds);
  }

  protected String decapitalizeFirstLetter(final String source) {
    if (source.length() == 0)
      return source;
    return ("" + source.charAt(0)).toLowerCase() + source.substring(1);
  }

  /**
   * Translates the given source name into an AADL compatible identifier.
   *
   * @param source
   *          Input name
   * @return AADL compatible identifier
   */
  public String getAadlTypeName(final String source) {
    return removeConsecutiveUnderscores(
        removeCharactersIllegalForAadl(source.replaceAll(" ", "_").replaceAll("-", "_").replace(".", "_"))).trim();
  }

  /**
   * Returns an ASN.1 compatible identifier name for the given element.
   *
   * @param packageName
   *          Source element's containing package name
   * @param source
   *          Source element's name
   * @param id
   *          Source element's ID
   * @return ASN.1 compatible identifier name
   */
  public String getAsn1IdentifierName(final String packageName, final String source, final String id) {
    /*
     * An "identifier" shall consist of an arbitrary number (one or more) of letters, digits, and hyphens. The initial
     * character shall be a lower-case letter. A hyphen shall not be the last character. A hyphen shall not be
     * immediately followed by another hyphen.
     *
     */
    String name = source.trim();

    if (getBooleanOptionValue(NameConverterOption.ForceAsn1IdentifierPrefix, false))
      name = getStringOptionValue(NameConverterOption.Asn1IdentifierPrefix, "") + name;
    else if (nameCollidesWithReservedIdentifier(name)
        && getBooleanOptionValue(NameConverterOption.AddAsn1IdentifierPrefixIfNecessary, false))
      name = getStringOptionValue(NameConverterOption.Asn1IdentifierPrefix, "") + name;

    name = removeLeadingNonLetters(removeTrailingHyphen(
        removeConsecutiveHyphens(removeCharactersIllegalForAsn1(replaceSpacesWithHyphens(name.trim())))));

    name = decapitalizeFirstLetter(name);

    if (getBooleanOptionValue(NameConverterOption.ResolveAsn1IdentifierConflicts, false))
      return dictionary.getTranslation(packageName, name, id);
    return name;
  }

  /**
   * Returns an ASN.1 compatible type name for the given element.
   *
   * @param packageName
   *          Source element's containing package name
   * @param source
   *          Source element's name
   * @param id
   *          Source element's ID
   * @return ASN.1 compatible type name
   */
  public String getAsn1TypeName(final String packageName, final String source, final String id) {
    /*
     * A "typereference" shall consist of an arbitrary number (one or more) of letters, digits, and hyphens. The initial
     * character shall be an upper-case letter. A hyphen shall not be the last character. A hyphen shall not be
     * immediately followed by another hyphen.
     */
    String name = source.trim();
    // Don't prefix types from the built-in null package.
    if (packageName.length() > 0) {
      if (getBooleanOptionValue(NameConverterOption.ForceAsn1TypePrefix, false))
        name = getStringOptionValue(NameConverterOption.Asn1TypePrefix, "") + name;
      else if (nameCollidesWithReservedIdentifier(name)
          && getBooleanOptionValue(NameConverterOption.AddAsn1TypePrefixIfNecessary, false))
        name = getStringOptionValue(NameConverterOption.Asn1TypePrefix, "") + name;
    }

    name = removeLeadingNonLetters(removeTrailingHyphen(
        removeConsecutiveHyphens(removeCharactersIllegalForAsn1(replaceSpacesWithHyphens(name.trim())))));

    name = capitalizeFirstLetter(name);

    if (getBooleanOptionValue(NameConverterOption.ResolveAsn1TypeConflicts, false))
      return dictionary.getTranslation(packageName, name, id);
    return name;
  }

  /**
   * Returns an ASN.1 compatible package name for the given package.
   *
   * @param source
   *          Source package's name
   * @param id
   *          Source package's ID
   * @return ASN.1 compatible package name
   */
  public String getPackageName(final String source, final String id) {
    /*
     * A "typereference" shall consist of an arbitrary number (one or more) of letters, digits, and hyphens. The initial
     * character shall be an upper-case letter. A hyphen shall not be the last character. A hyphen shall not be
     * immediately followed by another hyphen.
     */
    String name = source.trim();

    if (getBooleanOptionValue(NameConverterOption.ForcePackagePrefix, false))
      name = getStringOptionValue(NameConverterOption.PackagePrefix, "") + name;
    else if (nameCollidesWithReservedIdentifier(name)
        && getBooleanOptionValue(NameConverterOption.AddPackagePrefixIfNecessary, false))
      name = getStringOptionValue(NameConverterOption.PackagePrefix, "") + name;

    name = removeLeadingNonLetters(removeTrailingHyphen(
        removeConsecutiveHyphens(removeCharactersIllegalForAsn1(replaceSpacesWithHyphens(name.trim())))));

    name = capitalizeFirstLetter(name);

    return name;
  }

  protected boolean nameCollidesWithReservedIdentifier(final String name) {
    return reservedIdentifiers.contains(name.toLowerCase());
  }

  protected String removeCharactersIllegalForAadl(final String source) {
    return source.replaceAll("[^a-zA-Z0-9\\_]", "");
  }

  protected String removeCharactersIllegalForAsn1(final String source) {
    return source.replaceAll("[^a-zA-Z0-9\\-]", "");
  }

  protected String removeConsecutiveHyphens(final String source) {
    String mutableSource = source;
    while (mutableSource.contains("--"))
      mutableSource = mutableSource.replace("--", "-");
    return mutableSource;
  }

  protected String removeConsecutiveUnderscores(final String source) {
    // This function exists only because TASTE is incapable of handling AADL names
    // with consecutive underscores.
    String mutableSource = source;
    while (mutableSource.contains("__"))
      mutableSource = mutableSource.replace("__", "_");
    return mutableSource;
  }

  protected String removeLeadingNonLetters(final String source) {
    return source.replaceFirst("^[^a-zA-Z]*", "");
  }

  protected String removeTrailingHyphen(final String source) {
    String mutableSource = source;
    while (mutableSource.length() > 0 && mutableSource.charAt(mutableSource.length() - 1) == '-') {
      mutableSource = mutableSource.substring(0, mutableSource.length() - 1);
    }
    return mutableSource;
  }

  protected String replaceSpacesWithHyphens(final String source) {
    return source.replace(" ", "-");
  }

}
