// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;
import com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders.DeploymentViewDefinitionProvider;
import com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders.InterfaceViewDefinitionProvider;

public class AadlModelGeneratorTests {

	private final String REFERENCE_DV_2_FUNCTIONS_1_NODE = "---------------------------------------------------\r\n"
			+ "-- AADL2.1\r\n" + "-- TASTE type deploymentview\r\n" + "-- \r\n" + "-- generated code: do not edit\r\n"
			+ "---------------------------------------------------\r\n" + "\r\n"
			+ "PACKAGE deploymentview::DV::NodeComponent\r\n" + "PUBLIC\r\n" + "\r\n" + "WITH Deployment;\r\n"
			+ "WITH TASTE_DV_Properties;\r\n" + "WITH Taste;\r\n" + "\r\n" + "PROCESS NodeComponent_Partition\r\n"
			+ "END NodeComponent_Partition;\r\n" + "\r\n" + "PROCESS IMPLEMENTATION NodeComponent_Partition.others\r\n"
			+ "END NodeComponent_Partition.others;\r\n" + "\r\n" + "END deploymentview::DV::NodeComponent;\r\n" + "\r\n"
			+ "PACKAGE deploymentview::DV\r\n" + "PUBLIC\r\n" + "\r\n" + "WITH Deployment;\r\n"
			+ "WITH DummyPackage;\r\n" + "WITH TASTE_DV_Properties;\r\n" + "WITH Taste;\r\n"
			+ "WITH deploymentview::DV::NodeComponent;\r\n" + "WITH interfaceView::IV;\r\n"
			+ "WITH interfaceView::IV::Function1;\r\n" + "WITH interfaceView::IV::Function2;\r\n" + "\r\n"
			+ "SYSTEM NodeComponent\r\n" + "END NodeComponent;\r\n" + "\r\n"
			+ "SYSTEM IMPLEMENTATION NodeComponent.others\r\n" + "SUBCOMPONENTS\r\n"
			+ "  DummyProcessor : PROCESSOR DummyPackage::DummyProcessor {\r\n"
			+ "    Taste::coordinates => \"400 100 1600 550\";\r\n" + "  };\r\n"
			+ "  IV_Function1 : SYSTEM interfaceView::IV::Function1::Function1.others {\r\n"
			+ "    Taste::FunctionName => \"Function1\";\r\n" + "  };\r\n"
			+ "  IV_Function2 : SYSTEM interfaceView::IV::Function2::Function2.others {\r\n"
			+ "    Taste::FunctionName => \"Function2\";\r\n" + "  };\r\n"
			+ "  NodeComponent_Partition : PROCESS deploymentview::DV::NodeComponent::NodeComponent_Partition.others {\r\n"
			+ "    Taste::coordinates => \"600 150 1400 500\";\r\n" + "    Deployment::Port_Number => 0;\r\n"
			+ "  };\r\n" + "PROPERTIES\r\n"
			+ "  Actual_Processor_Binding => (reference (DummyProcessor)) APPLIES TO NodeComponent_Partition;\r\n"
			+ "  Taste::APLC_Binding => (reference (NodeComponent_Partition)) APPLIES TO IV_Function1;\r\n"
			+ "  Taste::APLC_Binding => (reference (NodeComponent_Partition)) APPLIES TO IV_Function2;\r\n"
			+ "END NodeComponent.others;\r\n" + "\r\n" + "SYSTEM deploymentview\r\n" + "END deploymentview;\r\n"
			+ "\r\n" + "SYSTEM IMPLEMENTATION deploymentview.others\r\n" + "SUBCOMPONENTS\r\n"
			+ "  NodeComponent : SYSTEM NodeComponent.others {\r\n"
			+ "    Taste::coordinates => \"200 50 1800 600\";\r\n" + "  };\r\n"
			+ "  interfaceView : SYSTEM interfaceView::IV::interfaceView.others;\r\n" + "END deploymentview.others;\r\n"
			+ "\r\n" + "PROPERTIES\r\n"
			+ "    Taste::HWLibraries => (\"/home/taste/tool-inst/share/ocarina/AADLv2/ocarina_components.aadl\");\r\n"
			+ "    Taste::coordinates => \"0 0 2000 650\";\r\n"
			+ "    Taste::interfaceView => \"InterfaceView.aadl\";\r\n" + "    Taste::version => \"2.0\";\r\n"
			+ "END deploymentview::DV;";

	private final String REFERENCE_IV_2_FUNCTIONS = "---------------------------------------------------\r\n"
			+ "-- AADL2.1\r\n" + "-- TASTE type interfaceview\r\n" + "-- \r\n" + "-- generated code: do not edit\r\n"
			+ "---------------------------------------------------\r\n" + "\r\n"
			+ "PACKAGE interfaceView::IV::Function1\r\n" + "PUBLIC\r\n" + "\r\n" + "WITH DataView;\r\n"
			+ "WITH TASTE_IV_Properties;\r\n" + "WITH Taste;\r\n" + "WITH interfaceView::IV::Function2;\r\n" + "\r\n"
			+ "SUBPROGRAM RI_DataExchange\r\n" + "FEATURES\r\n" + "  tUInt32 : IN PARAMETER DataView::TUInt32 {\r\n"
			+ "    Taste::encoding => NATIVE;\r\n" + "  };\r\n" + "END RI_DataExchange;\r\n" + "\r\n"
			+ "SUBPROGRAM IMPLEMENTATION RI_DataExchange.others\r\n" + "END RI_DataExchange.others;\r\n" + "\r\n"
			+ "SYSTEM Function1\r\n" + "FEATURES\r\n"
			+ "  RI_DataExchange : REQUIRES SUBPROGRAM ACCESS interfaceView::IV::Function2::PI_DataExchange.others {\r\n"
			+ "    Taste::coordinates => \"400 150\";\r\n" + "    Taste::InterfaceName => \"DataExchange\";\r\n"
			+ "    Taste::RCMoperationKind => any;\r\n" + "  };\r\n" + "PROPERTIES\r\n"
			+ "  Source_Language => (SDL);\r\n" + "  Taste::Active_Interfaces => any;\r\n" + "END Function1;\r\n"
			+ "\r\n" + "SYSTEM IMPLEMENTATION Function1.others\r\n" + "END Function1.others;\r\n" + "\r\n"
			+ "END interfaceView::IV::Function1;\r\n" + "\r\n" + "PACKAGE interfaceView::IV::Function2\r\n"
			+ "PUBLIC\r\n" + "\r\n" + "WITH DataView;\r\n" + "WITH TASTE_IV_Properties;\r\n" + "WITH Taste;\r\n"
			+ "\r\n" + "SUBPROGRAM PI_DataExchange\r\n" + "FEATURES\r\n"
			+ "  tUInt32 : IN PARAMETER DataView::TUInt32 {\r\n" + "    Taste::encoding => NATIVE;\r\n" + "  };\r\n"
			+ "PROPERTIES\r\n" + "  Taste::Associated_Queue_Size => 1;\r\n" + "END PI_DataExchange;\r\n" + "\r\n"
			+ "SUBPROGRAM IMPLEMENTATION PI_DataExchange.others\r\n" + "PROPERTIES\r\n"
			+ "  Compute_Execution_Time => 0 ms .. 1000 ms;\r\n" + "  Taste::Deadline => 0 ms;\r\n"
			+ "END PI_DataExchange.others;\r\n" + "\r\n" + "SYSTEM Function2\r\n" + "FEATURES\r\n"
			+ "  PI_DataExchange : PROVIDES SUBPROGRAM ACCESS interfaceView::IV::Function2::PI_DataExchange.others {\r\n"
			+ "    Taste::coordinates => \"400 490\";\r\n" + "    Taste::InterfaceName => \"DataExchange\";\r\n"
			+ "    Taste::RCMoperationKind => sporadic;\r\n" + "  };\r\n" + "PROPERTIES\r\n"
			+ "  Source_Language => (SDL);\r\n" + "  Taste::Active_Interfaces => any;\r\n" + "END Function2;\r\n"
			+ "\r\n" + "SYSTEM IMPLEMENTATION Function2.others\r\n" + "END Function2.others;\r\n" + "\r\n"
			+ "END interfaceView::IV::Function2;\r\n" + "\r\n" + "PACKAGE interfaceView::IV\r\n" + "PUBLIC\r\n" + "\r\n"
			+ "WITH DataView;\r\n" + "WITH TASTE_IV_Properties;\r\n" + "WITH Taste;\r\n"
			+ "WITH interfaceView::IV::Function1;\r\n" + "WITH interfaceView::IV::Function2;\r\n" + "\r\n"
			+ "SYSTEM interfaceView\r\n" + "END interfaceView;\r\n" + "\r\n"
			+ "SYSTEM IMPLEMENTATION interfaceView.others\r\n" + "SUBCOMPONENTS\r\n"
			+ "  Function1 : SYSTEM interfaceView::IV::Function1::Function1.others {\r\n"
			+ "    Taste::coordinates => \"200 50 600 150\";\r\n" + "  };\r\n"
			+ "  Function2 : SYSTEM interfaceView::IV::Function2::Function2.others {\r\n"
			+ "    Taste::coordinates => \"200 490 600 590\";\r\n" + "  };\r\n" + "CONNECTIONS\r\n"
			+ "  Function2_PI_DataExchange_Function1_RI_DataExchange : SUBPROGRAM ACCESS Function2.PI_DataExchange -> Function1.RI_DataExchange {\r\n"
			+ "    Taste::coordinates => \"400 150 400 490\";\r\n" + "  };\r\n" + "END interfaceView.others;\r\n"
			+ "\r\n" + "PROPERTIES\r\n" + "    Taste::coordinates => \"0 0 800 640\";\r\n"
			+ "    Taste::dataView => (\"DataView\");\r\n" + "    Taste::dataViewPath => (\"DataView.aadl\");\r\n"
			+ "    Taste::version => \"2.0\";\r\n" + "END interfaceView::IV;\r\n" + "";

	private final String REFERENCE_IV_2_FUNCTIONS_2_COMPONENTS = "---------------------------------------------------\r\n"
			+ "-- AADL2.1\r\n" + "-- TASTE type interfaceview\r\n" + "-- \r\n" + "-- generated code: do not edit\r\n"
			+ "---------------------------------------------------\r\n" + "\r\n"
			+ "PACKAGE interfaceView::IV::Function1\r\n" + "PUBLIC\r\n" + "\r\n" + "WITH DataView;\r\n"
			+ "WITH TASTE_IV_Properties;\r\n" + "WITH Taste;\r\n" + "WITH interfaceView::IV::Function2;\r\n" + "\r\n"
			+ "SUBPROGRAM RI_DataExchange\r\n" + "FEATURES\r\n" + "  tUInt32 : IN PARAMETER DataView::TUInt32 {\r\n"
			+ "    Taste::encoding => UPER;\r\n" + "  };\r\n" + "END RI_DataExchange;\r\n" + "\r\n"
			+ "SUBPROGRAM IMPLEMENTATION RI_DataExchange.others\r\n" + "END RI_DataExchange.others;\r\n" + "\r\n"
			+ "SYSTEM Function1\r\n" + "FEATURES\r\n"
			+ "  RI_DataExchange : REQUIRES SUBPROGRAM ACCESS interfaceView::IV::Function2::PI_DataExchange.others {\r\n"
			+ "    Taste::coordinates => \"400 150\";\r\n" + "    Taste::InterfaceName => \"DataExchange\";\r\n"
			+ "    Taste::RCMoperationKind => any;\r\n" + "  };\r\n" + "PROPERTIES\r\n"
			+ "  Source_Language => (SDL);\r\n" + "  Taste::Active_Interfaces => any;\r\n" + "END Function1;\r\n"
			+ "\r\n" + "SYSTEM IMPLEMENTATION Function1.others\r\n" + "END Function1.others;\r\n" + "\r\n"
			+ "END interfaceView::IV::Function1;\r\n" + "\r\n" + "PACKAGE interfaceView::IV::Function2\r\n"
			+ "PUBLIC\r\n" + "\r\n" + "WITH DataView;\r\n" + "WITH TASTE_IV_Properties;\r\n" + "WITH Taste;\r\n"
			+ "\r\n" + "SUBPROGRAM PI_DataExchange\r\n" + "FEATURES\r\n"
			+ "  tUInt32 : IN PARAMETER DataView::TUInt32 {\r\n" + "    Taste::encoding => UPER;\r\n" + "  };\r\n"
			+ "PROPERTIES\r\n" + "  Taste::Associated_Queue_Size => 1;\r\n" + "END PI_DataExchange;\r\n" + "\r\n"
			+ "SUBPROGRAM IMPLEMENTATION PI_DataExchange.others\r\n" + "PROPERTIES\r\n"
			+ "  Compute_Execution_Time => 0 ms .. 1000 ms;\r\n" + "  Taste::Deadline => 0 ms;\r\n"
			+ "END PI_DataExchange.others;\r\n" + "\r\n" + "SYSTEM Function2\r\n" + "FEATURES\r\n"
			+ "  PI_DataExchange : PROVIDES SUBPROGRAM ACCESS interfaceView::IV::Function2::PI_DataExchange.others {\r\n"
			+ "    Taste::coordinates => \"400 490\";\r\n" + "    Taste::InterfaceName => \"DataExchange\";\r\n"
			+ "    Taste::RCMoperationKind => sporadic;\r\n" + "  };\r\n" + "PROPERTIES\r\n"
			+ "  Source_Language => (SDL);\r\n" + "  Taste::Active_Interfaces => any;\r\n" + "END Function2;\r\n"
			+ "\r\n" + "SYSTEM IMPLEMENTATION Function2.others\r\n" + "END Function2.others;\r\n" + "\r\n"
			+ "END interfaceView::IV::Function2;\r\n" + "\r\n" + "PACKAGE interfaceView::IV\r\n" + "PUBLIC\r\n" + "\r\n"
			+ "WITH DataView;\r\n" + "WITH TASTE_IV_Properties;\r\n" + "WITH Taste;\r\n"
			+ "WITH interfaceView::IV::Function1;\r\n" + "WITH interfaceView::IV::Function2;\r\n" + "\r\n"
			+ "SYSTEM interfaceView\r\n" + "END interfaceView;\r\n" + "\r\n"
			+ "SYSTEM IMPLEMENTATION interfaceView.others\r\n" + "SUBCOMPONENTS\r\n"
			+ "  Function1 : SYSTEM interfaceView::IV::Function1::Function1.others {\r\n"
			+ "    Taste::coordinates => \"200 50 600 150\";\r\n" + "  };\r\n"
			+ "  Function2 : SYSTEM interfaceView::IV::Function2::Function2.others {\r\n"
			+ "    Taste::coordinates => \"200 490 600 590\";\r\n" + "  };\r\n" + "CONNECTIONS\r\n"
			+ "  Function2_PI_DataExchange_Function1_RI_DataExchange : SUBPROGRAM ACCESS Function2.PI_DataExchange -> Function1.RI_DataExchange {\r\n"
			+ "    Taste::coordinates => \"400 150 400 490\";\r\n" + "  };\r\n" + "END interfaceView.others;\r\n"
			+ "\r\n" + "PROPERTIES\r\n" + "    Taste::coordinates => \"0 0 800 640\";\r\n"
			+ "    Taste::dataView => (\"DataView\");\r\n" + "    Taste::dataViewPath => (\"DataView.aadl\");\r\n"
			+ "    Taste::version => \"2.0\";\r\n" + "END interfaceView::IV;\r\n" + "\r\n" + "";

	private final String REFERENCE_DV_2_FUNCTIONS_2_NODES = "---------------------------------------------------\r\n"
			+ "-- AADL2.1\r\n" + "-- TASTE type deploymentview\r\n" + "-- \r\n" + "-- generated code: do not edit\r\n"
			+ "---------------------------------------------------\r\n" + "\r\n"
			+ "PACKAGE deploymentview::DV::Node1\r\n" + "PUBLIC\r\n" + "\r\n" + "WITH Deployment;\r\n"
			+ "WITH DummyPackage;\r\n" + "WITH DummyPackage;\r\n" + "WITH TASTE_DV_Properties;\r\n" + "WITH Taste;\r\n"
			+ "\r\n" + "PROCESS Node1_Partition\r\n" + "END Node1_Partition;\r\n" + "\r\n"
			+ "PROCESS IMPLEMENTATION Node1_Partition.others\r\n" + "END Node1_Partition.others;\r\n" + "\r\n"
			+ "DEVICE Src\r\n" + "EXTENDS DummyPackage::DummyDevice\r\n" + "FEATURES\r\n"
			+ "  link : REFINED TO REQUIRES BUS ACCESS DummyPackage::DummyBus.i {\r\n"
			+ "    Taste::coordinates => \"1311 2804\";\r\n" + "  };\r\n" + "PROPERTIES\r\n"
			+ "  Taste::Interface_Coordinates => \"1126 1547\" APPLIES TO link;\r\n"
			+ "  Deployment::Help => \"Write your ASN.1 configuration here\";\r\n" + "  Deployment::Config => \"\";\r\n"
			+ "  Deployment::Configuration => {};\r\n" + "END Src;\r\n" + "\r\n"
			+ "DEVICE IMPLEMENTATION Src.others\r\n" + "EXTENDS DummyPackage::DummyDevice.device\r\n"
			+ "END Src.others;\r\n" + "\r\n" + "END deploymentview::DV::Node1;\r\n" + "\r\n"
			+ "PACKAGE deploymentview::DV::Node2\r\n" + "PUBLIC\r\n" + "\r\n" + "WITH Deployment;\r\n"
			+ "WITH DummyPackage;\r\n" + "WITH DummyPackage;\r\n" + "WITH TASTE_DV_Properties;\r\n" + "WITH Taste;\r\n"
			+ "\r\n" + "PROCESS Node2_Partition\r\n" + "END Node2_Partition;\r\n" + "\r\n"
			+ "PROCESS IMPLEMENTATION Node2_Partition.others\r\n" + "END Node2_Partition.others;\r\n" + "\r\n"
			+ "DEVICE Dst\r\n" + "EXTENDS DummyPackage::DummyDevice\r\n" + "FEATURES\r\n"
			+ "  link : REFINED TO REQUIRES BUS ACCESS DummyPackage::DummyBus.i {\r\n"
			+ "    Taste::coordinates => \"2278 600\";\r\n" + "  };\r\n" + "PROPERTIES\r\n"
			+ "  Taste::Interface_Coordinates => \"1241 1447\" APPLIES TO link;\r\n"
			+ "  Deployment::Help => \"Write your ASN.1 configuration here\";\r\n" + "  Deployment::Config => \"\";\r\n"
			+ "  Deployment::Configuration => {};\r\n" + "END Dst;\r\n" + "\r\n"
			+ "DEVICE IMPLEMENTATION Dst.others\r\n" + "EXTENDS DummyPackage::DummyDevice.device\r\n"
			+ "END Dst.others;\r\n" + "\r\n" + "END deploymentview::DV::Node2;\r\n" + "\r\n"
			+ "PACKAGE deploymentview::DV\r\n" + "PUBLIC\r\n" + "\r\n" + "WITH Deployment;\r\n"
			+ "WITH DummyPackage;\r\n" + "WITH DummyPackage;\r\n" + "WITH TASTE_DV_Properties;\r\n" + "WITH Taste;\r\n"
			+ "WITH deploymentview::DV::Node1;\r\n" + "WITH deploymentview::DV::Node2;\r\n"
			+ "WITH interfaceView::IV;\r\n" + "WITH interfaceView::IV::Function1;\r\n"
			+ "WITH interfaceView::IV::Function2;\r\n" + "\r\n" + "SYSTEM Node1\r\n" + "FEATURES\r\n"
			+ "  Src_Exchange : REQUIRES BUS ACCESS DummyPackage::DummyBus.i;\r\n" + "END Node1;\r\n" + "\r\n"
			+ "SYSTEM IMPLEMENTATION Node1.others\r\n" + "SUBCOMPONENTS\r\n"
			+ "  DummyProcessor : PROCESSOR DummyPackage::DummyProcessor {\r\n"
			+ "    Taste::coordinates => \"400 3004 1600 3304\";\r\n" + "  };\r\n"
			+ "  IV_Function1 : SYSTEM interfaceView::IV::Function1::Function1.others {\r\n"
			+ "    Taste::FunctionName => \"Function1\";\r\n" + "  };\r\n"
			+ "  Node1_Partition : PROCESS deploymentview::DV::Node1::Node1_Partition.others {\r\n"
			+ "    Taste::coordinates => \"600 3054 1400 3254\";\r\n" + "    Deployment::Port_Number => 0;\r\n"
			+ "  };\r\n" + "  Src : DEVICE deploymentview::DV::Node1::Src.others {\r\n"
			+ "    Taste::coordinates => \"400 2854 800 2954\";\r\n" + "  };\r\n" + "CONNECTIONS\r\n"
			+ "  Node1_Src_Exchange_Src_link : BUS ACCESS Src_Exchange -> Src.link;\r\n" + "PROPERTIES\r\n"
			+ "  Actual_Processor_Binding => (reference (DummyProcessor)) APPLIES TO Node1_Partition;\r\n"
			+ "  Actual_Processor_Binding => (reference (DummyProcessor)) APPLIES TO Src;\r\n"
			+ "  Taste::APLC_Binding => (reference (Node1_Partition)) APPLIES TO IV_Function1;\r\n"
			+ "END Node1.others;\r\n" + "\r\n" + "SYSTEM Node2\r\n" + "FEATURES\r\n"
			+ "  Dst_Exchange : REQUIRES BUS ACCESS DummyPackage::DummyBus.i;\r\n" + "END Node2;\r\n" + "\r\n"
			+ "SYSTEM IMPLEMENTATION Node2.others\r\n" + "SUBCOMPONENTS\r\n"
			+ "  Dst : DEVICE deploymentview::DV::Node2::Dst.others {\r\n"
			+ "    Taste::coordinates => \"1990 100 2390 200\";\r\n" + "  };\r\n"
			+ "  DummyProcessor : PROCESSOR DummyPackage::DummyProcessor {\r\n"
			+ "    Taste::coordinates => \"1990 250 3190 550\";\r\n" + "  };\r\n"
			+ "  IV_Function2 : SYSTEM interfaceView::IV::Function2::Function2.others {\r\n"
			+ "    Taste::FunctionName => \"Function2\";\r\n" + "  };\r\n"
			+ "  Node2_Partition : PROCESS deploymentview::DV::Node2::Node2_Partition.others {\r\n"
			+ "    Taste::coordinates => \"2190 300 2990 500\";\r\n" + "    Deployment::Port_Number => 0;\r\n"
			+ "  };\r\n" + "CONNECTIONS\r\n"
			+ "  Node2_Dst_Exchange_Dst_link : BUS ACCESS Dst_Exchange -> Dst.link;\r\n" + "PROPERTIES\r\n"
			+ "  Actual_Processor_Binding => (reference (DummyProcessor)) APPLIES TO Dst;\r\n"
			+ "  Actual_Processor_Binding => (reference (DummyProcessor)) APPLIES TO Node2_Partition;\r\n"
			+ "  Taste::APLC_Binding => (reference (Node2_Partition)) APPLIES TO IV_Function2;\r\n"
			+ "END Node2.others;\r\n" + "\r\n" + "SYSTEM deploymentview\r\n" + "END deploymentview;\r\n" + "\r\n"
			+ "SYSTEM IMPLEMENTATION deploymentview.others\r\n" + "SUBCOMPONENTS\r\n"
			+ "  Exchange : BUS DummyPackage::DummyBus.i {\r\n"
			+ "    Taste::coordinates => \"984 1447 1384 1547\";\r\n" + "  };\r\n"
			+ "  Node1 : SYSTEM Node1.others {\r\n" + "    Taste::coordinates => \"200 2804 1800 3354\";\r\n"
			+ "  };\r\n" + "  Node2 : SYSTEM Node2.others {\r\n" + "    Taste::coordinates => \"1790 50 3390 600\";\r\n"
			+ "  };\r\n" + "  interfaceView : SYSTEM interfaceView::IV::interfaceView.others;\r\n" + "CONNECTIONS\r\n"
			+ "  deploymentview_Exchange_Node1_Src_Exchange : BUS ACCESS Exchange -> Node1.Src_Exchange {\r\n"
			+ "    Taste::coordinates => \"1311 2804 1311 2175 1126 2175 1126 1547\";\r\n" + "  };\r\n"
			+ "  deploymentview_Exchange_Node2_Dst_Exchange : BUS ACCESS Exchange -> Node2.Dst_Exchange {\r\n"
			+ "    Taste::coordinates => \"2278 600 2278 1023 1241 1023 1241 1447\";\r\n" + "  };\r\n"
			+ "PROPERTIES\r\n"
			+ "  Actual_Connection_Binding => (reference (Exchange)) APPLIES TO interfaceView.Function2_PI_DataExchange_Function1_RI_DataExchange;\r\n"
			+ "END deploymentview.others;\r\n" + "\r\n" + "PROPERTIES\r\n"
			+ "    Taste::HWLibraries => (\"/home/taste/tool-inst/share/ocarina/AADLv2/ocarina_components.aadl\");\r\n"
			+ "    Taste::coordinates => \"0 0 3590 3404\";\r\n"
			+ "    Taste::interfaceView => \"InterfaceView.aadl\";\r\n" + "    Taste::version => \"2.0\";\r\n"
			+ "END deploymentview::DV;";

	private final String REFERENCE_DV_2_FUNCTIONS_2_PARTITIONS = "---------------------------------------------------\r\n"
			+ "-- AADL2.1\r\n" + "-- TASTE type deploymentview\r\n" + "-- \r\n" + "-- generated code: do not edit\r\n"
			+ "---------------------------------------------------\r\n" + "\r\n"
			+ "PACKAGE deploymentview::DV::NodeComponent\r\n" + "PUBLIC\r\n" + "\r\n" + "WITH Deployment;\r\n"
			+ "WITH TASTE_DV_Properties;\r\n" + "WITH Taste;\r\n" + "\r\n" + "PROCESS NodeComponent_Partition\r\n"
			+ "END NodeComponent_Partition;\r\n" + "\r\n" + "PROCESS IMPLEMENTATION NodeComponent_Partition.others\r\n"
			+ "END NodeComponent_Partition.others;\r\n" + "\r\n" + "END deploymentview::DV::NodeComponent;\r\n" + "\r\n"
			+ "PACKAGE deploymentview::DV\r\n" + "PUBLIC\r\n" + "\r\n" + "WITH Deployment;\r\n"
			+ "WITH DummyPackage;\r\n" + "WITH TASTE_DV_Properties;\r\n" + "WITH Taste;\r\n"
			+ "WITH deploymentview::DV::NodeComponent;\r\n" + "WITH interfaceView::IV;\r\n"
			+ "WITH interfaceView::IV::Function1;\r\n" + "WITH interfaceView::IV::Function2;\r\n" + "\r\n"
			+ "SYSTEM NodeComponent\r\n" + "END NodeComponent;\r\n" + "\r\n"
			+ "SYSTEM IMPLEMENTATION NodeComponent.others\r\n" + "SUBCOMPONENTS\r\n"
			+ "  DummyProcessor : PROCESSOR DummyPackage::DummyProcessor {\r\n"
			+ "    Taste::coordinates => \"400 100 1600 550\";\r\n" + "  };\r\n"
			+ "  IV_Function1 : SYSTEM interfaceView::IV::Function1::Function1.others {\r\n"
			+ "    Taste::FunctionName => \"Function1\";\r\n" + "  };\r\n"
			+ "  IV_Function2 : SYSTEM interfaceView::IV::Function2::Function2.others {\r\n"
			+ "    Taste::FunctionName => \"Function2\";\r\n" + "  };\r\n"
			+ "  NodeComponent_Partition : PROCESS deploymentview::DV::NodeComponent::NodeComponent_Partition.others {\r\n"
			+ "    Taste::coordinates => \"600 150 1400 500\";\r\n" + "    Deployment::Port_Number => 0;\r\n"
			+ "  };\r\n" + "PROPERTIES\r\n"
			+ "  Actual_Processor_Binding => (reference (DummyProcessor)) APPLIES TO NodeComponent_Partition;\r\n"
			+ "  Taste::APLC_Binding => (reference (NodeComponent_Partition)) APPLIES TO IV_Function1;\r\n"
			+ "  Taste::APLC_Binding => (reference (NodeComponent_Partition)) APPLIES TO IV_Function2;\r\n"
			+ "END NodeComponent.others;\r\n" + "\r\n" + "SYSTEM deploymentview\r\n" + "END deploymentview;\r\n"
			+ "\r\n" + "SYSTEM IMPLEMENTATION deploymentview.others\r\n" + "SUBCOMPONENTS\r\n"
			+ "  NodeComponent : SYSTEM NodeComponent.others {\r\n"
			+ "    Taste::coordinates => \"200 50 1800 600\";\r\n" + "  };\r\n"
			+ "  interfaceView : SYSTEM interfaceView::IV::interfaceView.others;\r\n" + "END deploymentview.others;\r\n"
			+ "\r\n" + "PROPERTIES\r\n"
			+ "    Taste::HWLibraries => (\"/home/taste/tool-inst/share/ocarina/AADLv2/ocarina_components.aadl\");\r\n"
			+ "    Taste::coordinates => \"0 0 2000 650\";\r\n"
			+ "    Taste::interfaceView => \"InterfaceView.aadl\";\r\n" + "    Taste::version => \"2.0\";\r\n"
			+ "END deploymentview::DV;";

	private AadlModelGenerator generator;
	private NameConverter nameConverter;
	private InterfaceViewDefinitionProvider interfaceViewProvider;
	private DeploymentViewDefinitionProvider deploymentViewProvider;

	@Before
	public void setUp() throws Exception {
		nameConverter = new NameConverter();
		deploymentViewProvider = new DeploymentViewDefinitionProvider(nameConverter);
		interfaceViewProvider = new InterfaceViewDefinitionProvider(nameConverter);

		generator = new AadlModelGenerator(nameConverter, interfaceViewProvider, deploymentViewProvider);
	}

	void assertCodeEquals(final String actual, final String reference) {
		final String[] actualLines = actual.trim().split("\n");
		final String[] referenceLines = reference.trim().split("\n");
		assertEquals(actualLines.length, referenceLines.length);
		for (int i = 0; i < actualLines.length; i++) {
			assertEquals(actualLines[i].trim(), referenceLines[i].trim());
		}
	}

	@Test
	public void testGenerateAadlDeploymentViewFromAbstractDataModel() {
		final SystemModel model = SystemModelCreator.generateSystemModelWith2FunctionsAndOneEventExchange();

		final ModelItems items = generator.generateAadlSystemModelFromAbstractSystemModel(model);

		final ModelItem item = items.getItemByName(DeploymentViewDefinitionProvider.DEPLOYMENT_VIEW_NAME);
		final String aadl = new String(item.buffer.array());
		assertNotNull(aadl);
		assertCodeEquals(aadl, REFERENCE_DV_2_FUNCTIONS_1_NODE);
	}

	@Test
	public void testGenerateAadlInterfaceViewFromAbstractDataModel() {
		final SystemModel model = SystemModelCreator.generateSystemModelWith2FunctionsAndOneEventExchange();

		final ModelItems items = generator.generateAadlSystemModelFromAbstractSystemModel(model);

		final ModelItem item = items.getItemByName(InterfaceViewDefinitionProvider.INTERFACE_VIEW_NAME);
		final String aadl = new String(item.buffer.array());
		assertNotNull(aadl);
		assertCodeEquals(aadl, REFERENCE_IV_2_FUNCTIONS);
	}

	@Test
	public void testGenerateAadlViewsFromAbstractDataModel() {
		final SystemModel model = SystemModelCreator
				.generateSystemModelWith2FunctionsAndOneEventExchangeHostedBy2Components();

		final ModelItems items = generator.generateAadlSystemModelFromAbstractSystemModel(model);

		final ModelItem interfaceView = items.getItemByName(InterfaceViewDefinitionProvider.INTERFACE_VIEW_NAME);
		final String interfaceViewAadl = new String(interfaceView.buffer.array());
		assertNotNull(interfaceViewAadl);
		assertCodeEquals(interfaceViewAadl, REFERENCE_IV_2_FUNCTIONS_2_COMPONENTS);

		final ModelItem deploymentView = items.getItemByName(DeploymentViewDefinitionProvider.DEPLOYMENT_VIEW_NAME);
		final String deploymentViewAadl = new String(deploymentView.buffer.array());
		assertNotNull(deploymentViewAadl);
		assertCodeEquals(deploymentViewAadl, REFERENCE_DV_2_FUNCTIONS_2_NODES);
	}

	@Test
	public void testCombinePartitionsAndGenerateAadlViewsFromAbstractDataModel() {
		final SystemModel model = SystemModelCreator
				.generateSystemModelWith2FunctionsOn2PartitionsAndOneEventExchange();

		final ModelItems items = generator.generateAadlSystemModelFromAbstractSystemModel(model);

		final ModelItem interfaceView = items.getItemByName(InterfaceViewDefinitionProvider.INTERFACE_VIEW_NAME);
		final String interfaceViewAadl = new String(interfaceView.buffer.array());
		assertNotNull(interfaceViewAadl);
		assertCodeEquals(interfaceViewAadl, REFERENCE_IV_2_FUNCTIONS);

		final ModelItem deploymentView = items.getItemByName(DeploymentViewDefinitionProvider.DEPLOYMENT_VIEW_NAME);
		final String deploymentViewAadl = new String(deploymentView.buffer.array());
		assertNotNull(deploymentViewAadl);
		assertCodeEquals(deploymentViewAadl, REFERENCE_DV_2_FUNCTIONS_2_PARTITIONS);
	}

}
