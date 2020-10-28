// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.tasteintegration;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.n7space.capellatasteplugin.tasteintegration.SystemCommandExecutor.SystemProcessExecutor;

public class SystemCommandExecutorTest {

	@Test
	public void testExecuteProcess() {
		final SystemCommandExecutor parent = new SystemCommandExecutor();
		final SystemProcessExecutor executor = parent.new SystemProcessExecutor();
		final List<String> command = new LinkedList<String>();
		command.add("invalid program");
		command.add("argument");
		executor.setCommand(command);
		executor.setDirectory("path");

		try {
			executor.startProcess();
		} catch (Throwable t) {
		}

		try {
			executor.getResult();
		} catch (Throwable t) {
		}

		final String log = executor.getLog();
		assertEquals(log,
				"Setting command to:invalid program:argument:;Setting directory to:path;Starting process;Retrieving result;");
	}

}
