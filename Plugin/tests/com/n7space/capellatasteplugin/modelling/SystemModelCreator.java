// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling;

import com.n7space.capellatasteplugin.modelling.architecture.Component;
import com.n7space.capellatasteplugin.modelling.architecture.ComponentExchange;
import com.n7space.capellatasteplugin.modelling.architecture.ComponentPort;
import com.n7space.capellatasteplugin.modelling.architecture.Direction;
import com.n7space.capellatasteplugin.modelling.architecture.Function;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionExchange;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionExchange.ExchangeParameter;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionExchange.ExchangeType;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionPort;
import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;
import com.n7space.capellatasteplugin.modelling.data.IntegerDataType;

public class SystemModelCreator {

	public static SystemModel generateSystemModelWith2FunctionsAndOneEventExchange() {
		final DataModel dataModel = new DataModel();
		final DataPackage pkg = new DataPackage("DataPackage", "DataPackageId");
		dataModel.dataPackages.add(pkg);
		final IntegerDataType type = new IntegerDataType(pkg, "T_UInt32", "T_UInt32");
		pkg.addTypeDefinition(type);
		final SystemModel model = new SystemModel("System", "systemId", dataModel);
		final Component nodeComponent = new Component(model, "NodeComponent", "nodeComponentId", true);
		nodeComponent.processor.typeClass = "DummyPackage::DummyProcessor";
		final Component partitionComponent = new Component(model, "PartitionComponent", "partitionComponentId", false);

		final Function function1 = new Function(partitionComponent, "Function1", "function1Id");
		function1.language = "SDL";
		final Function function2 = new Function(partitionComponent, "Function2", "function2Id");
		function2.language = "SDL";
		final FunctionExchange exchange = new FunctionExchange(function1, function2, "DataExchange", "dataExchangeId",
				ExchangeType.EVENT);

		exchange.parameters.add(new ExchangeParameter(type.name, new DataTypeReference(type), false));

		final FunctionPort sourcePort = new FunctionPort(function1, "SourcePort", "sourcePortId", Direction.OUT,
				exchange);
		final FunctionPort destinationPort = new FunctionPort(function2, "DestinationPort", "destinationPortId",
				Direction.IN, exchange);
		function1.ports.add(sourcePort);
		function2.ports.add(destinationPort);
		partitionComponent.allocatedFunctions.add(function1);
		partitionComponent.allocatedFunctions.add(function2);
		nodeComponent.deployedComponents.add(partitionComponent);
		model.functionExchanges.add(exchange);
		model.components.add(nodeComponent);
		model.components.add(partitionComponent);
		model.topLevelComponents.add(nodeComponent);
		model.functions.add(function1);
		model.functions.add(function2);
		return model;
	}

	public static SystemModel generateSystemModelWith2FunctionsOn2PartitionsAndOneEventExchange() {
		final DataModel dataModel = new DataModel();
		final DataPackage pkg = new DataPackage("DataPackage", "DataPackageId");
		dataModel.dataPackages.add(pkg);
		final IntegerDataType type = new IntegerDataType(pkg, "T_UInt32", "T_UInt32");
		pkg.addTypeDefinition(type);
		final SystemModel model = new SystemModel("System", "systemId", dataModel);
		final Component nodeComponent = new Component(model, "NodeComponent", "nodeComponentId", true);
		nodeComponent.processor.typeClass = "DummyPackage::DummyProcessor";
		final Component partitionComponent1 = new Component(model, "PartitionComponent1", "partitionComponent1Id",
				false);
		final Component partitionComponent2 = new Component(model, "PartitionComponent2", "partitionComponent2Id",
				false);

		final Function function1 = new Function(partitionComponent1, "Function1", "function1Id");
		function1.language = "SDL";
		final Function function2 = new Function(partitionComponent2, "Function2", "function2Id");
		function2.language = "SDL";
		final FunctionExchange exchange = new FunctionExchange(function1, function2, "DataExchange", "dataExchangeId",
				ExchangeType.EVENT);

		exchange.parameters.add(new ExchangeParameter(type.name, new DataTypeReference(type), false));

		final FunctionPort sourcePort = new FunctionPort(function1, "SourcePort", "sourcePortId", Direction.OUT,
				exchange);
		final FunctionPort destinationPort = new FunctionPort(function2, "DestinationPort", "destinationPortId",
				Direction.IN, exchange);
		function1.ports.add(sourcePort);
		function2.ports.add(destinationPort);
		partitionComponent1.allocatedFunctions.add(function1);
		partitionComponent2.allocatedFunctions.add(function2);
		nodeComponent.deployedComponents.add(partitionComponent1);
		nodeComponent.deployedComponents.add(partitionComponent2);
		model.functionExchanges.add(exchange);
		model.components.add(nodeComponent);
		model.components.add(partitionComponent1);
		model.components.add(partitionComponent2);
		model.topLevelComponents.add(nodeComponent);
		model.functions.add(function1);
		model.functions.add(function2);
		return model;
	}

	public static SystemModel generateSystemModelWith2FunctionsAndOneEventExchangeHostedBy2Components() {
		final DataModel dataModel = new DataModel();
		final DataPackage pkg = new DataPackage("DataPackage", "DataPackageId");
		dataModel.dataPackages.add(pkg);
		final IntegerDataType type = new IntegerDataType(pkg, "T_UInt32", "T_UInt32");
		pkg.addTypeDefinition(type);
		final SystemModel model = new SystemModel("System", "systemId", dataModel);
		final Component partitionComponent1 = new Component(model, "Partition1", "partitionComponent1Id", false);
		final Component partitionComponent2 = new Component(model, "Partition2", "partiotionComponent2Id", false);

		final Component nodeComponent1 = new Component(model, "Node1", "nodeComponent1Id", true);
		nodeComponent1.processor.typeClass = "DummyPackage::DummyProcessor";
		final Component nodeComponent2 = new Component(model, "Node2", "nodeComponent2Id", true);
		nodeComponent2.processor.typeClass = "DummyPackage::DummyProcessor";

		final Function function1 = new Function(partitionComponent1, "Function1", "function1Id");
		function1.language = "SDL";
		final Function function2 = new Function(partitionComponent2, "Function2", "function2Id");
		function2.language = "SDL";
		final FunctionExchange functionExchange = new FunctionExchange(function1, function2, "DataExchange",
				"dataExchangeId", ExchangeType.EVENT);
		functionExchange.parameters.add(new ExchangeParameter(type.name, new DataTypeReference(type), false));
		final FunctionPort sourcePort = new FunctionPort(function1, "SourcePort", "sourcePortId", Direction.OUT,
				functionExchange);
		final FunctionPort destinationPort = new FunctionPort(function2, "DestinationPort", "destinationPortId",
				Direction.IN, functionExchange);
		function1.ports.add(sourcePort);
		function2.ports.add(destinationPort);
		partitionComponent1.allocatedFunctions.add(function1);
		partitionComponent2.allocatedFunctions.add(function2);

		final ComponentExchange componentExchange = new ComponentExchange(nodeComponent1, nodeComponent2, "Exchange",
				"componentExchangeId");
		componentExchange.typeClass = "DummyPackage::DummyBus.i";
		final ComponentPort sourceComponentPort = new ComponentPort(nodeComponent1, "Src", "srcId", componentExchange);
		sourceComponentPort.implementationTypeClass = "DummyPackage::DummyDevice.device";
		sourceComponentPort.typeClass = "DummyPackage::DummyDevice";
		final ComponentPort destinationComponentPort = new ComponentPort(nodeComponent2, "Dst", "dstId",
				componentExchange);
		destinationComponentPort.implementationTypeClass = "DummyPackage::DummyDevice.device";
		destinationComponentPort.typeClass = "DummyPackage::DummyDevice";
		nodeComponent1.ports.add(sourceComponentPort);
		nodeComponent2.ports.add(destinationComponentPort);

		componentExchange.allocatedFunctionExchanges.add(functionExchange);
		nodeComponent1.deployedComponents.add(partitionComponent1);
		nodeComponent2.deployedComponents.add(partitionComponent2);
		model.functionExchanges.add(functionExchange);
		model.componentExchanges.add(componentExchange);
		model.components.add(partitionComponent1);
		model.components.add(partitionComponent2);
		model.components.add(nodeComponent1);
		model.components.add(nodeComponent2);
		model.topLevelComponents.add(nodeComponent1);
		model.topLevelComponents.add(nodeComponent2);
		model.functions.add(function1);
		model.functions.add(function2);
		return model;
	}

}
