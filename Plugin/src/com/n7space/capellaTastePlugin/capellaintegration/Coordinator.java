// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.datavalue.BinaryExpression;
import org.polarsys.capella.core.data.information.datavalue.UnaryExpression;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.BinaryExpressionInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.BooleanElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.ClassModelElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.CollectionElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataElementInterpretationProviderStub;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataPackageInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.EnumeratedElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.ExchangeItemElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.NumericElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.NumericValueElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.StringElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.StringValueElementIntepretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.UnaryExpressionInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.mmi.DataModelBrowser;
import com.n7space.capellatasteplugin.capellaintegration.mmi.DataModelBrowser.DataModelPresentationCallback;
import com.n7space.capellatasteplugin.capellaintegration.mmi.DirectoryBrowser;
import com.n7space.capellatasteplugin.capellaintegration.mmi.IssuePresenter;
import com.n7space.capellatasteplugin.capellaintegration.mmi.MessagePresenter;
import com.n7space.capellatasteplugin.capellaintegration.mmi.MessagePresenter.MessageKind;
import com.n7space.capellatasteplugin.capellaintegration.mmi.PresentationFeedback;
import com.n7space.capellatasteplugin.capellaintegration.mmi.SystemModelBrowser;
import com.n7space.capellatasteplugin.capellaintegration.mmi.SystemModelBrowser.SystemModelPresentationCallback;
import com.n7space.capellatasteplugin.eclipseintegration.PathVault;
import com.n7space.capellatasteplugin.eclipseintegration.SettingsProvider;
import com.n7space.capellatasteplugin.modelling.AadlModelGenerator;
import com.n7space.capellatasteplugin.modelling.Asn1ElementDefinitionGenerator;
import com.n7space.capellatasteplugin.modelling.Asn1ModelGenerator;
import com.n7space.capellatasteplugin.modelling.ModelItems;
import com.n7space.capellatasteplugin.modelling.ModelSerializer;
import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.architecture.ArchitectureElement;
import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;
import com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders.DeploymentViewDefinitionProvider;
import com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders.InterfaceViewDefinitionProvider;
import com.n7space.capellatasteplugin.modelling.data.BooleanDataType;
import com.n7space.capellatasteplugin.modelling.data.CollectionDataType;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.EnumeratedDataType;
import com.n7space.capellatasteplugin.modelling.data.FloatDataType;
import com.n7space.capellatasteplugin.modelling.data.IntegerDataType;
import com.n7space.capellatasteplugin.modelling.data.StringDataType;
import com.n7space.capellatasteplugin.modelling.data.StructuredDataType;
import com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders.BooleanTypeDefinitionProvider;
import com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders.CollectionTypeDefinitionProvider;
import com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders.DataValueDefinitionProvider;
import com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders.EnumeratedTypeDefinitionProvider;
import com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders.FloatTypeDefinitionProvider;
import com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders.IntegerTypeDefinitionProvider;
import com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders.StringTypeDefinitionProvider;
import com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders.StructuredTypeDefinitionProvider;
import com.n7space.capellatasteplugin.tasteintegration.TasteCommandExecutor;
import com.n7space.capellatasteplugin.utils.ConfigurableObject;
import com.n7space.capellatasteplugin.utils.Issue;
import com.n7space.capellatasteplugin.utils.OptionsHelper;

/**
 * Coordinator which coordinates the interaction between plugin elements.
 *
 * A singleton which manages the conversion flow - from initial data model
 * conversion, through user-interaction, up to the serialization of the
 * resulting models.
 */
public class Coordinator implements ConfigurableObject {
	private static final Coordinator instance = new Coordinator();

	/**
	 * Returns the Coordinator singleton instance.
	 *
	 * @return Coordinator instance
	 */
	public static Coordinator getInstance() {
		return instance;
	}

	protected final CapellaSystemModelInterpreter systemModelInterpreter;
	protected final CapellaDataModelInterpreter dataModelInterpreter;
	protected final Asn1ModelGenerator asn1ModelGenerator;
	protected final AadlModelGenerator aadlModelGenerator;
	protected final NameConverter nameConverter = new NameConverter();
	protected final Asn1ElementDefinitionGenerator asn1ElementDefinitionGenerator = new Asn1ElementDefinitionGenerator();
	protected final DataModelElementInterpreter dataModelElementInterpreter = new DataModelElementInterpreter();
	protected final InterfaceViewDefinitionProvider interfaceViewProvider;
	protected final DeploymentViewDefinitionProvider deploymentViewProvider;

	// I/O related classes, to be externally set.
	protected ModelSerializer modelSerializer = null;
	protected IssuePresenter issuePresenter = null;
	protected DataModelBrowser dataModelBrowser = null;
	protected SystemModelBrowser systemModelBrowser = null;
	protected DirectoryBrowser directoryBrowser = null;
	protected MessagePresenter messagePresenter = null;

	private Coordinator() {

		asn1ElementDefinitionGenerator.registerDefinitionProvider(CollectionDataType.class,
				new CollectionTypeDefinitionProvider(nameConverter));
		asn1ElementDefinitionGenerator.registerDefinitionProvider(BooleanDataType.class,
				new BooleanTypeDefinitionProvider(nameConverter));
		asn1ElementDefinitionGenerator.registerDefinitionProvider(StructuredDataType.class,
				new StructuredTypeDefinitionProvider(nameConverter));
		asn1ElementDefinitionGenerator.registerDefinitionProvider(IntegerDataType.class,
				new IntegerTypeDefinitionProvider(nameConverter));
		asn1ElementDefinitionGenerator.registerDefinitionProvider(FloatDataType.class,
				new FloatTypeDefinitionProvider(nameConverter));
		asn1ElementDefinitionGenerator.registerDefinitionProvider(StringDataType.class,
				new StringTypeDefinitionProvider(nameConverter));
		asn1ElementDefinitionGenerator.registerDefinitionProvider(EnumeratedDataType.class,
				new EnumeratedTypeDefinitionProvider(nameConverter));
		asn1ElementDefinitionGenerator.registerDefinitionProvider(DataType.DataTypeValue.class,
				new DataValueDefinitionProvider(nameConverter));

		dataModelElementInterpreter.registerInterpretationProvider(BinaryExpression.class,
				new BinaryExpressionInterpretationProvider());
		dataModelElementInterpreter.registerInterpretationProvider(UnaryExpression.class,
				new UnaryExpressionInterpretationProvider());
		dataModelElementInterpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.DataPkg.class, new DataPackageInterpretationProvider());
		dataModelElementInterpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.datatype.BooleanType.class,
				new BooleanElementInterpretationProvider());
		dataModelElementInterpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.datatype.NumericType.class,
				new NumericElementInterpretationProvider());
		dataModelElementInterpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.datatype.StringType.class,
				new StringElementInterpretationProvider());
		dataModelElementInterpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.datatype.Enumeration.class,
				new EnumeratedElementInterpretationProvider(dataModelElementInterpreter));
		dataModelElementInterpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue.class,
				new NumericValueElementInterpretationProvider());
		dataModelElementInterpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.datavalue.LiteralStringValue.class,
				new StringValueElementIntepretationProvider());
		dataModelElementInterpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.ExchangeItem.class,
				new ExchangeItemElementInterpretationProvider());
		dataModelElementInterpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.Collection.class,
				new CollectionElementInterpretationProvider());
		// This handles both Classes and Unions, as Union is a specialization of Class.
		dataModelElementInterpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.Class.class, new ClassModelElementInterpretationProvider());

		// Stubs to avoid warnings - expressions.
		dataModelElementInterpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.datavalue.OpaqueExpression.class,
				new DataElementInterpretationProviderStub());
		// Stubs to avoid warnings - references.
		dataModelElementInterpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.datavalue.BooleanReference.class,
				new DataElementInterpretationProviderStub());
		dataModelElementInterpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.datavalue.NumericReference.class,
				new DataElementInterpretationProviderStub());
		dataModelElementInterpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.datavalue.ComplexValueReference.class,
				new DataElementInterpretationProviderStub());
		dataModelElementInterpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.datavalue.StringReference.class,
				new DataElementInterpretationProviderStub());
		dataModelElementInterpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.datavalue.EnumerationReference.class,
				new DataElementInterpretationProviderStub());

		dataModelInterpreter = new CapellaDataModelInterpreter(dataModelElementInterpreter);
		asn1ModelGenerator = new Asn1ModelGenerator(nameConverter, asn1ElementDefinitionGenerator);

		deploymentViewProvider = new DeploymentViewDefinitionProvider(nameConverter);
		interfaceViewProvider = new InterfaceViewDefinitionProvider(nameConverter);

		systemModelInterpreter = new CapellaSystemModelInterpreter(dataModelElementInterpreter, nameConverter);
		aadlModelGenerator = new AadlModelGenerator(nameConverter, interfaceViewProvider, deploymentViewProvider);

		final Option[] options = getOptions();
		if (!SettingsProvider.getInstance().restoreOptionValues(options, SettingsProvider.PREFERENCES_NODE_NAME)) {
			SettingsProvider.getInstance().storeOptionValues(options, SettingsProvider.PREFERENCES_NODE_NAME);
		}
	}

	/**
	 * Returns the list of options supported by the managed plugin elements.
	 *
	 * @return List of options.
	 */
	@Override
	public Option[] getOptions() {
		Option[] options = new Option[0];
		options = OptionsHelper.addOptions(options, nameConverter.getOptions());
		options = OptionsHelper.addOptions(options, asn1ElementDefinitionGenerator.getOptions());
		options = OptionsHelper.addOptions(options, dataModelElementInterpreter.getOptions());
		options = OptionsHelper.addOptions(options, deploymentViewProvider.getOptions());
		options = OptionsHelper.addOptions(options, TasteCommandExecutor.getInstance().getOptions());		
		options = OptionsHelper.addOptions(options, aadlModelGenerator.getOptions());
		return options;
	}

	/**
	 * Returns value of the option identified by the given handle.
	 *
	 * @param optionHandle
	 *            Option handle
	 * @return Option value or null if no option is found for the given handle
	 */
	@Override
	public Object getOptionValue(final Object optionHandle) {
		return OptionsHelper.getOptionValue(getOptions(), optionHandle);
	}

	protected void processAadlSystemModelGeneration(final SystemModel systemModel, final DataModel dataModel) {
		final ModelItems systemItems = aadlModelGenerator.generateAadlSystemModelFromAbstractSystemModel(systemModel);
		final ModelItems dataItems = asn1ModelGenerator.generateAsn1DataModelFromAbstractDataModel(dataModel);
		systemItems.add(dataItems.getItemByName("DataView"));
		processAadlSystemModelSerialization(systemModel, systemItems);
	}

	protected void processAadlSystemModelSerialization(final SystemModel systemModel, final ModelItems items) {
		final String path = directoryBrowser.getOutputDirectory("AADL System Model directory");
		if (path != null) {
			try {
				TasteCommandExecutor.getInstance().intializeTasteProjectIfApplicable(path);
				PathVault.getInstance().setPath(systemModel.id, path, PathVault.STORAGE_NODE_NAME);
				modelSerializer.serialize(path, items);
				messagePresenter.presentMessage(MessageKind.Information, "Success",
						"AADL System Model was successfully serialized");
				TasteCommandExecutor.getInstance().autogenerateArtefactsAfterExportIfApplicable(path);
			} catch (final IOException e) {
				e.printStackTrace();
				messagePresenter.presentMessage(MessageKind.Error, "AADL System Model serialization error",
						"The following error occurred while serializing the model: " + e.toString());
			}
		}
	}

	/**
	 * Invokes the processing flow for the given Capella physical architecture.
	 *
	 * @param architecture
	 *            Capella physical architecture
	 */
	public void processArchitecture(final PhysicalArchitecture architecture) {
		syncOptionsFromStorage();
		final HashSet<String> validIds = IdScrapper.getAllIds(architecture);
		nameConverter.cleanupTranslations(validIds);
		final List<Issue> issues = new LinkedList<Issue>();
		final DataModel dataModel = new DataModel();
		final SystemModel systemModel = systemModelInterpreter
				.convertCapellaSystemModelToAbstractSystemModel(architecture, dataModel, issues);
		dataModelInterpreter.createPostfixesFromUnits(dataModel);
		if (issues.size() == 0)
			processSystemModelSubsetSelection(systemModel, dataModel);
		else {
			issuePresenter.PresentIssues(issues, new IssuePresenter.IssuePresentationCallback() {

				@Override
				public void onIssuePresentationFeedback(final List<Issue> issues, final PresentationFeedback feedback) {
					if (feedback == PresentationFeedback.Accepted)
						processSystemModelSubsetSelection(systemModel, dataModel);
				}
			});
		}
	}

	protected void processAsn1DataModelGeneration(final DataModel dataModel) {
		final ModelItems items = asn1ModelGenerator.generateAsn1DataModelFromAbstractDataModel(dataModel);
		processAsn1DataModelSerialization(items);
	}

	protected void processAsn1DataModelSerialization(final ModelItems items) {
		final String path = directoryBrowser.getOutputDirectory("ASN.1 Data Model directory");
		if (path != null) {
			try {
				modelSerializer.serialize(path, items);
				messagePresenter.presentMessage(MessageKind.Information, "Success",
						"ASN.1 Data Model was successfully serialized");
			} catch (final IOException e) {
				e.printStackTrace();
				messagePresenter.presentMessage(MessageKind.Error, "ASN.1 Data Model serialization error",
						"The following error occurred while serializing the model: " + e.toString());
			}
		}
	}

	protected void processDataModelSubsetSelection(final DataModel dataModel) {
		dataModelBrowser.captureDataModelSubsetSelection(dataModel, new DataModelPresentationCallback() {

			@Override
			public void onDataModelPresentationCallback(final DataModel dataModel,
					final List<DataModelElement> selection, final PresentationFeedback feedback) {
				if (feedback != PresentationFeedback.Accepted)
					return;
				final DataModel selectedSubmodel = new DataModel();
				for (final DataPackage candidatePackage : dataModel.dataPackages) {
					final DataPackage clone = candidatePackage.cloneSubset(selection);
					if (clone != null)
						selectedSubmodel.dataPackages.add(clone);
				}
				processAsn1DataModelGeneration(selectedSubmodel);
			}
		});
	}

	/**
	 * Invokes the processing flow for the given Capella data model.
	 *
	 * @param selection
	 *            Capella data model
	 */
	public void processDataPackages(final List<DataPkg> selection) {
		syncOptionsFromStorage();
		final HashSet<String> validIds = IdScrapper.getAllIds(selection);
		nameConverter.cleanupTranslations(validIds);
		final List<Issue> issues = new LinkedList<Issue>();
		final DataModel dataModel = dataModelInterpreter.convertCapellaDataModelToAbstractDataModel(selection, issues);
		if (issues.size() == 0)
			processDataModelSubsetSelection(dataModel);
		else {
			issuePresenter.PresentIssues(issues, new IssuePresenter.IssuePresentationCallback() {

				@Override
				public void onIssuePresentationFeedback(final List<Issue> issues, final PresentationFeedback feedback) {
					if (feedback == PresentationFeedback.Accepted)
						processDataModelSubsetSelection(dataModel);
				}
			});
		}
	}

	protected void processSystemModelSubsetSelection(final SystemModel systemModel, final DataModel dataModel) {
		systemModelBrowser.captureSystemModelSubsetSelection(systemModel, new SystemModelPresentationCallback() {

			@Override
			public void onSystemModelPresentationCallback(final SystemModel model,
					final List<ArchitectureElement> selection, final PresentationFeedback feedback) {
				if (feedback != PresentationFeedback.Accepted)
					return;

				final Set<ArchitectureElement> allElements = new HashSet<ArchitectureElement>();
				allElements.addAll(model.components);
				allElements.addAll(model.functions);
				for (final ArchitectureElement element : allElements) {
					if (!selection.contains(element)) {
						model.removeElementAndItsDependencies(element);
					}
				}

				processAadlSystemModelGeneration(model, dataModel);
			}
		});
	}

	/**
	 * Sets the data model browser for interaction with the user.
	 *
	 * @param browser
	 *            Data model browser
	 */
	public void setDataModelBrowser(final DataModelBrowser browser) {
		dataModelBrowser = browser;
	}

	/**
	 * Sets the directory browser for interaction with the user.
	 *
	 * @param browser
	 *            Directory browser.
	 */
	public void setDirectoryBrowser(final DirectoryBrowser browser) {
		directoryBrowser = browser;
	}

	/**
	 * Sets the issue presenter for interaction with the user.
	 *
	 * @param presenter
	 *            Issue presenter.
	 */
	public void setIssuePresenter(final IssuePresenter presenter) {
		issuePresenter = presenter;
	}

	/**
	 * Sets the message presenter for interaction with the user.
	 *
	 * @param presenter
	 *            Message presenter.
	 */
	public void setMessagePresenter(final MessagePresenter presenter) {
		messagePresenter = presenter;
	}

	/**
	 * Sets the model serializer for output model serialization.
	 *
	 * @param serializer
	 *            Model serializer.
	 */
	public void setModelSerializer(final ModelSerializer serializer) {
		modelSerializer = serializer;
	}

	/**
	 * Sets the options for the managed plugin elements.
	 *
	 * @param options
	 *            Options
	 */
	@Override
	public void setOptions(final Option[] options) {
		nameConverter.setOptions(options);
		asn1ElementDefinitionGenerator.setOptions(options);
		dataModelElementInterpreter.setOptions(options);
		deploymentViewProvider.setOptions(options);
		TasteCommandExecutor.getInstance().setOptions(options);
		aadlModelGenerator.setOptions(options);
	}

	/**
	 * Sets - for all the managed plugin elements - the option value for the given
	 * option handle.
	 *
	 * @param optionHandle
	 *            Option handle
	 * @param value
	 *            Option value
	 */
	@Override
	public void setOptionValue(final Object optionHandle, final Object value) {
		nameConverter.setOptionValue(optionHandle, value);
		asn1ElementDefinitionGenerator.setOptionValue(optionHandle, value);
		dataModelElementInterpreter.setOptionValue(optionHandle, value);
		deploymentViewProvider.setOptionValue(optionHandle, value);
	}

	/**
	 * Sets the system model browser for interaction with the user.
	 *
	 * @param browser
	 *            System model browser
	 */
	public void setSystemModelBrowser(final SystemModelBrowser browser) {
		systemModelBrowser = browser;
	}

	/**
	 * Restores from storage the values of all relevant options.
	 */
	public void syncOptionsFromStorage() {
		final Option[] options = getOptions();
		SettingsProvider.getInstance().restoreOptionValues(options, SettingsProvider.PREFERENCES_NODE_NAME);
		setOptions(options);
	}

}
