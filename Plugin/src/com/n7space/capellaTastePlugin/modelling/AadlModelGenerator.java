// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.LinkedList;

import com.n7space.capellatasteplugin.modelling.ModelItem.Kind;
import com.n7space.capellatasteplugin.modelling.architecture.Component;
import com.n7space.capellatasteplugin.modelling.architecture.ComponentPort;
import com.n7space.capellatasteplugin.modelling.architecture.Function;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionExchange;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionPort;
import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlElement.UsageContext;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlPackage;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlPackages;
import com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders.DeploymentViewDefinitionProvider;
import com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders.InterfaceViewDefinitionProvider;
import com.n7space.capellatasteplugin.utils.BasicConfigurableItem;

/**
 * Class for generating a TASTE compatible AADL.
 *
 */
public class AadlModelGenerator extends BasicConfigurableItem {
  
  /**
   * Enumeration listing possible NameConverter option's handles.
   */
  public static enum AadlModelGeneratorOption {
    /**
     * Merge GUIs within a single node
     */
    MergeGUIs("MergeGUIs");
    /**
     * Prefix for option handles.
     */
    public final static String NAME_COVERTER_PREFIX = "com.n7space.capellatasteplugin.modelling.AadlModelGenerator.";

    private final String value;

    private AadlModelGeneratorOption(final String baseName) {
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

  protected final NameConverter nameConverter;
  protected final InterfaceViewDefinitionProvider ivProvider;
  protected final DeploymentViewDefinitionProvider dvProvider;
  
  protected final Option[] OPTIONS = {
      new Option(AadlModelGeneratorOption.MergeGUIs, "Merge GUIs within a single node", Boolean.TRUE)
  };

  /**
   * The constructor.
   *
   * @param converter
   *          Name converter
   * @param interfaceViewProvider
   *          Provider of InterfaceView AADL definitions.
   * @param deploymentViewProvider
   *          Provider of DeploymentView AADL definitions.
   */
  public AadlModelGenerator(final NameConverter converter, final InterfaceViewDefinitionProvider interfaceViewProvider,
      final DeploymentViewDefinitionProvider deploymentViewProvider) {
    nameConverter = converter;
    ivProvider = interfaceViewProvider;
    dvProvider = deploymentViewProvider;
    setOptions(OPTIONS);
  }

  /**
   * Generates InterfaceView and DataView AADL from the given system model
   *
   * @param systemModel
   *          System model
   * @return Generated model elements
   */
  public ModelItems generateAadlSystemModelFromAbstractSystemModel(final SystemModel systemModel) {
    combineNodePartitions(systemModel);
    renamePartitions(systemModel);
    if (getBooleanOptionValue(AadlModelGeneratorOption.MergeGUIs, false))
      combineGuisIntoOnePerNode(systemModel);
    final AadlPackages interfaceViewPackages = ivProvider.generateInterfaceViewPackages(systemModel);
    final AadlPackages deploymentViewPackages = dvProvider.generateDeploymentViewPackages(systemModel,
        interfaceViewPackages);
    final ModelItems items = new ModelItems();
    items.items.addAll(generateAadlInterfaceViewFromAadlPackages(systemModel, interfaceViewPackages).items);
    items.items.addAll(generateAadlDeploymentViewFromAadlPackages(systemModel, deploymentViewPackages).items);
    return items;
  }

  protected void clearAllDeployedComponents(final Component parent) {
    for (final Component component : parent.deployedComponents)
      clearAllDeployedComponents(component);
    parent.deployedComponents.clear();
  }

  protected void combineNodePartitions(final SystemModel systemModel) {
    for (final Component node : systemModel.topLevelComponents) {
      combineNodePartitions(systemModel, node);
    }
  }

  protected void changeParentToCombinedMaster(final FunctionExchange exchange, final HashSet<Function> oldParents,
      final Function newParent) {
    if (oldParents.contains(exchange.requiringFunction))
      exchange.requiringFunction = newParent;
    if (oldParents.contains(exchange.providingFunction))
      exchange.providingFunction = newParent;
    exchange.parents.clear();
    exchange.parents.add(exchange.requiringFunction);
    exchange.parents.add(exchange.providingFunction);
  }

  protected HashSet<Function> getAllGuiFunctions(final SystemModel systemModel, final Component node,
      final Component component) {
    final HashSet<Function> guiFunctions = new HashSet<>();
    for (final Function function : component.allocatedFunctions) {
      if ("GUI".equals(function.language.toUpperCase()))
        guiFunctions.add(function);
    }
    return guiFunctions;
  }

  protected boolean areThereInterGuiInteractions(final HashSet<Function> guiFunctions) {
    for (final Function function : guiFunctions) {
      for (final FunctionPort port : function.ports) {
        if (guiFunctions.contains(port.exchange.providingFunction)
            && guiFunctions.contains(port.exchange.requiringFunction))
          return true;
      }
    }
    return false;
  }

  protected void gatherGuiResponsibilities(final SystemModel systemModel, final Component component,
      final HashSet<Function> guiFunctions, final Function masterGuiFunction) {
    for (final Function guiFunction : guiFunctions) {
      systemModel.functions.remove(guiFunction);
      component.allocatedFunctions.remove(guiFunction);
      masterGuiFunction.directives.addAll(guiFunction.directives);
      masterGuiFunction.ports.addAll(guiFunction.ports);
      System.out.println("Removing GUI Function " + guiFunction.name);
    }
    for (final FunctionPort port : masterGuiFunction.ports) {
      port.setTheOnlyParent(masterGuiFunction);
      changeParentToCombinedMaster(port.exchange, guiFunctions, masterGuiFunction);
    }
    systemModel.functions.add(masterGuiFunction);
    component.allocatedFunctions.add(masterGuiFunction);
    System.out.println("Adding GUI Function " + masterGuiFunction.name);
  }

  protected void combineAllGuisWithinANode(final SystemModel systemModel, final Component node,
      final Component component) {
    final HashSet<Function> guiFunctions = getAllGuiFunctions(systemModel, node, component);
    if (guiFunctions.size() == 0)
      return;
    if (areThereInterGuiInteractions(guiFunctions))
      return;
    final Function masterGuiFunction = new Function(component, node.name + "-GUI", node.name + "-GUI-ID");
    masterGuiFunction.language = "GUI";
    gatherGuiResponsibilities(systemModel, component, guiFunctions, masterGuiFunction);

  }

  protected void combineGuisIntoOnePerNode(final SystemModel systemModel) {
    for (final Component node : systemModel.topLevelComponents) {
      final HashSet<Component> allComponents = new HashSet<Component>();
      gatherAllDeployedComponents(node, allComponents);
      if (allComponents.size() == 1)
        combineAllGuisWithinANode(systemModel, node, allComponents.iterator().next());
    }
  }

  protected void combineNodePartitions(final SystemModel systemModel, final Component node) {
    String id = "";
    String name = "";
    final LinkedList<Function> functions = new LinkedList<Function>();
    final LinkedList<ComponentPort> ports = new LinkedList<ComponentPort>();
    final HashSet<Component> allComponents = new HashSet<Component>();

    gatherAllDeployedComponents(node, allComponents);
    if (allComponents.size() <= 1) // Nothing to combine
      return;

    for (final Component component : allComponents) {
      id += component.id;
      name += component.name;
      functions.addAll(component.allocatedFunctions);
      ports.addAll(component.ports);
      for (final ComponentPort port : component.ports) {
        port.exchange.parents.remove(component);
      }
      systemModel.components.remove(component);
    }

    final Component newComponent = new Component(node, name, id, false);
    newComponent.allocatedFunctions.addAll(functions);
    newComponent.ports.addAll(ports);
    for (final ComponentPort port : newComponent.ports) {
      port.setTheOnlyParent(newComponent);
      port.exchange.parents.add(newComponent);
    }
    for (final Function function : newComponent.allocatedFunctions) {
      function.setTheOnlyParent(newComponent);
    }

    clearAllDeployedComponents(node);
    node.deployedComponents.add(newComponent);
    systemModel.components.add(newComponent);
  }

  protected void gatherAllDeployedComponents(final Component parent, final HashSet<Component> components) {
    for (final Component component : parent.deployedComponents) {
      components.add(component);
      gatherAllDeployedComponents(component, components);
    }
  }

  protected ModelItems generateAadlDeploymentViewFromAadlPackages(final SystemModel systemModel,
      final AadlPackages pkgs) {
    final StringBuilder sb = new StringBuilder();
    sb.append("---------------------------------------------------\r\n" + "-- AADL2.1\r\n"
        + "-- TASTE type deploymentview\r\n" + "-- \r\n" + "-- generated code: do not edit\r\n"
        + "---------------------------------------------------\n\n");

    for (final AadlPackage pkg : pkgs.packages) {
      pkg.sortContents();
      sb.append(pkg.serializeToString(new UsageContext(), ""));
    }

    final ByteBuffer buffer = ByteBuffer.wrap(sb.toString().getBytes(Charset.forName("UTF-8")));
    final ModelItem item = new ModelItem(DeploymentViewDefinitionProvider.DEPLOYMENT_VIEW_NAME, Kind.AADL, buffer);
    final ModelItems result = new ModelItems();
    result.items.add(item);
    return result;
  }

  protected ModelItems generateAadlInterfaceViewFromAadlPackages(final SystemModel systemModel,
      final AadlPackages pkgs) {
    final StringBuilder sb = new StringBuilder();
    sb.append("---------------------------------------------------\n" + "-- AADL2.1\n" + "-- TASTE type interfaceview\n"
        + "-- \n" + "-- generated code: do not edit\n" + "---------------------------------------------------\n\n");

    for (final AadlPackage pkg : pkgs.packages) {
      pkg.sortContents();
      sb.append(pkg.serializeToString(new UsageContext(), ""));
    }
    final ByteBuffer buffer = ByteBuffer.wrap(sb.toString().getBytes(Charset.forName("UTF-8")));
    final ModelItem item = new ModelItem(InterfaceViewDefinitionProvider.INTERFACE_VIEW_NAME, Kind.AADL, buffer);
    final ModelItems result = new ModelItems();
    result.items.add(item);
    return result;
  }

  protected void renamePartitions(final SystemModel systemModel) {
    for (final Component node : systemModel.topLevelComponents) {
      for (final Component partition : node.deployedComponents) {
        partition.name = node.name + "-Partition";
      }
    }
  }

}
