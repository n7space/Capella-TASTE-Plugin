// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NameConverterTests {

	@Test
	public void forcedAndRequiredAsn1IdentifierPrefixesDoNotInterfere() {
		final NameConverter converter = new NameConverter();
		converter.setOptionValue(NameConverter.NameConverterOption.AddAsn1IdentifierPrefixIfNecessary, Boolean.TRUE);
		converter.setOptionValue(NameConverter.NameConverterOption.ForceAsn1IdentifierPrefix, Boolean.TRUE);
		converter.setOptionValue(NameConverter.NameConverterOption.Asn1IdentifierPrefix, "Prefix");
		assertEquals(converter.getAsn1IdentifierName("", "INTERSECTION", ""), "prefixINTERSECTION");
		assertEquals(converter.getAsn1IdentifierName("", "intersection", ""), "prefixintersection");
		assertEquals(converter.getAsn1IdentifierName("", "intersectionX", ""), "prefixintersectionX");
	}

	@Test
	public void forcedAndRequiredAsn1TypePrefixesDoNotInterfere() {
		final NameConverter converter = new NameConverter();
		converter.setOptionValue(NameConverter.NameConverterOption.AddAsn1TypePrefixIfNecessary, Boolean.TRUE);
		converter.setOptionValue(NameConverter.NameConverterOption.ForceAsn1TypePrefix, Boolean.TRUE);
		converter.setOptionValue(NameConverter.NameConverterOption.Asn1TypePrefix, "Prefix");
		assertEquals(converter.getAsn1TypeName("DummyPkg", "INTERSECTION", ""), "PrefixINTERSECTION");
		assertEquals(converter.getAsn1TypeName("DummyPkg", "intersection", ""), "Prefixintersection");
		assertEquals(converter.getAsn1TypeName("DummyPkg", "intersectionX", ""), "PrefixintersectionX");
	}

	@Test
	public void generatesCorrectAsn1IdentifierNames() {
		final NameConverter converter = new NameConverter();
		assertEquals(converter.getAsn1IdentifierName("", "my-valid-id", ""), "my-valid-id");
		assertEquals(converter.getAsn1IdentifierName("", "my invalid id", ""), "my-invalid-id");
		assertEquals(converter.getAsn1IdentifierName("", "-my invalid id", ""), "my-invalid-id");
		assertEquals(converter.getAsn1IdentifierName("", "My invalid id", ""), "my-invalid-id");
		assertEquals(converter.getAsn1IdentifierName("", "012My invalid id", ""), "my-invalid-id");
		assertEquals(converter.getAsn1IdentifierName("", "my-invalid--id", ""), "my-invalid-id");
		assertEquals(converter.getAsn1IdentifierName("", "my-invalid-id-", ""), "my-invalid-id");
	}

	@Test
	public void generatesCorrectAsn1TypeNames() {
		final NameConverter converter = new NameConverter();
		assertEquals(converter.getAsn1TypeName("DummyPkg", "My-valid-id", ""), "My-valid-id");
		assertEquals(converter.getAsn1TypeName("DummyPkg", "my invalid id", ""), "My-invalid-id");
		assertEquals(converter.getAsn1TypeName("DummyPkg", "-my invalid id", ""), "My-invalid-id");
		assertEquals(converter.getAsn1TypeName("DummyPkg", "My invalid id", ""), "My-invalid-id");
		assertEquals(converter.getAsn1TypeName("DummyPkg", "012My invalid id", ""), "My-invalid-id");
		assertEquals(converter.getAsn1TypeName("DummyPkg", "my-invalid--id", ""), "My-invalid-id");
		assertEquals(converter.getAsn1TypeName("DummyPkg", "my-invalid-id-", ""), "My-invalid-id");
	}

	@Test
	public void handlesAsn1IdentifierNameCollisionsWithReservedIdentifiers() {
		final NameConverter converter = new NameConverter();
		converter.setOptionValue(NameConverter.NameConverterOption.AddAsn1IdentifierPrefixIfNecessary, Boolean.TRUE);
		converter.setOptionValue(NameConverter.NameConverterOption.Asn1IdentifierPrefix, "Prefix");
		assertEquals(converter.getAsn1IdentifierName("", "INTERSECTION", ""), "prefixINTERSECTION");
		assertEquals(converter.getAsn1IdentifierName("", "intersection", ""), "prefixintersection");
		assertEquals(converter.getAsn1IdentifierName("", "intersectionX", ""), "intersectionX");
	}

	@Test
	public void handlesAsn1TypeNameCollisionsWithReservedIdentifiers() {
		final NameConverter converter = new NameConverter();
		converter.setOptionValue(NameConverter.NameConverterOption.AddAsn1TypePrefixIfNecessary, Boolean.TRUE);
		converter.setOptionValue(NameConverter.NameConverterOption.Asn1TypePrefix, "Prefix");
		assertEquals(converter.getAsn1TypeName("DummyPkg", "INTERSECTION", ""), "PrefixINTERSECTION");
		assertEquals(converter.getAsn1TypeName("DummyPkg", "intersection", ""), "Prefixintersection");
		assertEquals(converter.getAsn1TypeName("DummyPkg", "intersectionX", ""), "IntersectionX");
	}

	@Test
	public void prefixesAsn1IdentifierNamesIfForced() {
		final NameConverter converter = new NameConverter();
		converter.setOptionValue(NameConverter.NameConverterOption.ForceAsn1IdentifierPrefix, Boolean.TRUE);
		converter.setOptionValue(NameConverter.NameConverterOption.Asn1IdentifierPrefix, "Prefix");
		assertEquals(converter.getAsn1IdentifierName("", "INTERSECTION", ""), "prefixINTERSECTION");
		assertEquals(converter.getAsn1IdentifierName("", "intersection", ""), "prefixintersection");
		assertEquals(converter.getAsn1IdentifierName("", "intersectionX", ""), "prefixintersectionX");
	}

	@Test
	public void prefixesAsn1TypeNamesIfForced() {
		final NameConverter converter = new NameConverter();
		converter.setOptionValue(NameConverter.NameConverterOption.ForceAsn1TypePrefix, Boolean.TRUE);
		converter.setOptionValue(NameConverter.NameConverterOption.Asn1TypePrefix, "Prefix");
		assertEquals(converter.getAsn1TypeName("DummyPkg", "INTERSECTION", ""), "PrefixINTERSECTION");
		assertEquals(converter.getAsn1TypeName("DummyPkg", "intersection", ""), "Prefixintersection");
		assertEquals(converter.getAsn1TypeName("DummyPkg", "intersectionX", ""), "PrefixintersectionX");
	}

	@Test
	public void resolvesAsn1IdentifierConflicts_providesDifferentNamesForDifferentIds() {
		final NameConverter converter = new NameConverter();
		converter.setOptionValue(NameConverter.NameConverterOption.ResolveAsn1IdentifierConflicts, Boolean.TRUE);
		assertEquals(converter.getAsn1IdentifierName("", "myId", "0"), "myId");
		assertEquals(converter.getAsn1IdentifierName("", "myId", "1"), "myId2");
	}

	@Test
	public void resolvesAsn1IdentifierConflicts_providesTheSameNameForTheSameId() {
		final NameConverter converter = new NameConverter();
		converter.setOptionValue(NameConverter.NameConverterOption.ResolveAsn1IdentifierConflicts, Boolean.TRUE);
		assertEquals(converter.getAsn1IdentifierName("", "myId", "0"), "myId");
		assertEquals(converter.getAsn1IdentifierName("", "myId", "0"), "myId");
	}

	@Test
	public void resolvesAsn1IdentifierConflicts_takesIntoAccountPackageNames() {
		final NameConverter converter = new NameConverter();
		converter.setOptionValue(NameConverter.NameConverterOption.ResolveAsn1IdentifierConflicts, Boolean.TRUE);
		assertEquals(converter.getAsn1IdentifierName("Pkg1", "myId", "0"), "myId");
		assertEquals(converter.getAsn1IdentifierName("Pkg2", "myId", "0"), "myId");
	}

	@Test
	public void resolvesAsn1TypeConflicts_providesDifferentNamesForDifferentIds() {
		final NameConverter converter = new NameConverter();
		converter.setOptionValue(NameConverter.NameConverterOption.ResolveAsn1TypeConflicts, Boolean.TRUE);
		assertEquals(converter.getAsn1TypeName("DummyPkg", "MyType", "0"), "MyType");
		assertEquals(converter.getAsn1TypeName("DummyPkg", "MyType", "1"), "MyType2");
	}

	@Test
	public void resolvesAsn1TypeConflicts_providesTheSameNameForTheSameId() {
		final NameConverter converter = new NameConverter();
		converter.setOptionValue(NameConverter.NameConverterOption.ResolveAsn1TypeConflicts, Boolean.TRUE);
		assertEquals(converter.getAsn1TypeName("DummyPkg", "MyType", "0"), "MyType");
		assertEquals(converter.getAsn1TypeName("DummyPkg", "MyType", "0"), "MyType");
	}

	@Test
	public void resolvesAsn1TypeConflicts_takesIntoAccountPackageNames() {
		final NameConverter converter = new NameConverter();
		converter.setOptionValue(NameConverter.NameConverterOption.ResolveAsn1TypeConflicts, Boolean.TRUE);
		assertEquals(converter.getAsn1TypeName("Pkg1", "MyType", "0"), "MyType");
		assertEquals(converter.getAsn1TypeName("Pkg2", "MyType", "0"), "MyType");
	}

}
