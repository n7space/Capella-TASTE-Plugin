// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.polarsys.capella.core.data.capellacommon.StateEvent;
import org.polarsys.capella.core.data.information.Association;
import org.polarsys.capella.core.data.information.Class;
import org.polarsys.capella.core.data.information.Collection;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.ExchangeItem;
import org.polarsys.capella.core.data.information.KeyPart;
import org.polarsys.capella.core.data.information.Unit;
import org.polarsys.capella.core.data.information.communication.Exception;
import org.polarsys.capella.core.data.information.communication.Message;
import org.polarsys.capella.core.data.information.communication.MessageReference;
import org.polarsys.capella.core.data.information.communication.Signal;
import org.polarsys.capella.core.data.information.datatype.DataType;
import org.polarsys.capella.core.data.information.datavalue.DataValue;

public class DataPackageMock extends BaseTypeMock implements DataPkg {

	public EList<ExchangeItem> exchangeItems = new BasicEList<ExchangeItem>();
	public EList<DataPkg> dataPkgs = new BasicEList<DataPkg>();
	public EList<Class> classes = new BasicEList<Class>();
	public EList<Collection> collections = new BasicEList<Collection>();
	public EList<DataType> dataTypes = new BasicEList<DataType>();
	public EList<DataValue> dataValues = new BasicEList<DataValue>();

	public DataPackageMock(final DataPkg pkg, final String name, final String id) {
		super(pkg, name, id);
	}

	public DataPackageMock(final String name, final String id) {
		super(null, name, id);
	}

	@Override
	public EList<Association> getOwnedAssociations() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<Class> getOwnedClasses() {
		return classes;
	}

	@Override
	public EList<Collection> getOwnedCollections() {
		return collections;
	}

	@Override
	public EList<DataPkg> getOwnedDataPkgs() {
		return dataPkgs;
	}

	@Override
	public EList<DataType> getOwnedDataTypes() {
		return dataTypes;
	}

	@Override
	public EList<DataValue> getOwnedDataValues() {
		return dataValues;
	}

	@Override
	public EList<Exception> getOwnedExceptions() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<ExchangeItem> getOwnedExchangeItems() {
		return exchangeItems;
	}

	@Override
	public EList<KeyPart> getOwnedKeyParts() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<MessageReference> getOwnedMessageReferences() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<Message> getOwnedMessages() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<Signal> getOwnedSignals() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<StateEvent> getOwnedStateEvents() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<Unit> getOwnedUnits() {
		throw new RuntimeException("Not implemented");
	}

}
