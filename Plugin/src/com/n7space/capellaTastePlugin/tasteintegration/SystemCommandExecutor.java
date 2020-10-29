// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.tasteintegration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Class facilitating execution of system commands.
 *
 */
public class SystemCommandExecutor {

	/**
	 * Execution result.
	 *
	 */
	public static class CommandExecutionResult {
		/**
		 * Is execution successful.
		 */
		final public boolean isSuccessful;
		/**
		 * Output.
		 */
		final public String output;

		/**
		 * A constructor.
		 *
		 * @param success
		 *            Is execution successful
		 * @param executionOutput
		 *            Output
		 */
		public CommandExecutionResult(final boolean success, final String executionOutput) {
			output = executionOutput;
			isSuccessful = success;
		}
	}

	/**
	 * Process executor factory interface.
	 *
	 */
	public interface ProcessExecutorFactoryInterface {
		/**
		 * Creates process executor.
		 *
		 * @return Process executor.
		 */
		ProcessExecutorInterface createExecutor();
	}

	/**
	 * Process executor interface.
	 *
	 */
	public interface ProcessExecutorInterface {
		/**
		 * Gets process's input stream.
		 *
		 * @return Process's input stream.
		 */
		InputStream getInputStream();

		/**
		 * Gets execution result code.
		 *
		 * @return Result code
		 * @throws InterruptedException
		 */
		int getResult() throws InterruptedException;

		/**
		 * Sets command to be executed by the process.
		 *
		 * @param command
		 *            Command to be executed, including arguments.
		 */
		void setCommand(final List<String> command);

		/**
		 * Sets command's working directory.
		 *
		 * @param path
		 *            Path to working directory.
		 */
		void setDirectory(final String path);

		/**
		 * Starts command execution.
		 *
		 * @throws IOException
		 */
		void startProcess() throws IOException;
	}

	/**
	 * System process executor.
	 *
	 */
	public class SystemProcessExecutor implements ProcessExecutorInterface {

		private List<String> startCommand;
		private String workingPath;
		private Process process;
		private StringBuilder log = new StringBuilder();

		/**
		 * Returns log of the invoked activities.
		 * 
		 * @return Log
		 */
		public String getLog() {
			return log.toString();
		}

		/**
		 * {@inheritDoc}}
		 */
		@Override
		public InputStream getInputStream() {
			log.append("Retrieving input stream;");
			return process.getInputStream();
		}

		/**
		 * {@inheritDoc}}
		 */
		@Override
		public int getResult() throws InterruptedException {
			log.append("Retrieving result;");
			return process.waitFor();
		}

		/**
		 * {@inheritDoc}}
		 */
		@Override
		public void setCommand(final List<String> command) {
			log.append("Setting command to:");
			for (String part : command)
				log.append(part + ":");
			log.append(";");
			startCommand = command;
		}

		/**
		 * {@inheritDoc}}
		 */
		@Override
		public void setDirectory(final String path) {
			log.append("Setting directory to:" + path + ";");
			workingPath = path;

		}

		/**
		 * {@inheritDoc}}
		 */
		@Override
		public void startProcess() throws IOException {
			log.append("Starting process;");
			final ProcessBuilder builder = new ProcessBuilder(startCommand);
			builder.redirectErrorStream(true);
			builder.directory(new File(workingPath));
			process = builder.start();
		}

	}

	/**
	 * System process executor factory.
	 *
	 */
	public class SystemProcessExecutorFactory implements ProcessExecutorFactoryInterface {

		/**
		 * {@inheritDoc}}
		 */
		@Override
		public ProcessExecutorInterface createExecutor() {
			return new SystemProcessExecutor();
		}

	}

	protected ProcessExecutorFactoryInterface factory = new SystemProcessExecutorFactory();

	/**
	 * Executes the given process, with the given arguments, in the given working
	 * directory and optionally waits for execution result.
	 * 
	 * @param directory
	 *            Working directory
	 * @param processName
	 *            Name of the process to be invoked
	 * @param arguments
	 *            Command arguments
	 * @param waitForFinish
	 *            Whether to wait for execution completion
	 * @return Execution result (automatically successful if waitForFinish == false)
	 */
	public CommandExecutionResult executeProcess(final String directory, final String processName,
			final List<String> arguments, final boolean waitForFinish) {
		try {
			final List<String> command = new LinkedList<String>();
			for (final String commandPart : processName.split("\\s+")) {
				if (!commandPart.isEmpty())
					command.add(commandPart);
			}
			if (arguments != null)
				command.addAll(arguments);
			final ProcessExecutorInterface process = factory.createExecutor();
			process.setCommand(command);
			process.setDirectory(directory);
			process.startProcess();
			if (!waitForFinish)
				return new CommandExecutionResult(true, null);
			final InputStream is = process.getInputStream();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			final StringBuilder output = new StringBuilder();
			final int result = process.getResult();
			while (reader.ready())
				output.append(reader.readLine());
			return new CommandExecutionResult(result == 0, output.toString());
		} catch (final Exception e) {
			return new CommandExecutionResult(false, e.toString());
		}
	}

	/**
	 * Sets the factory for process executors.
	 * 
	 * @param processExecutorFactory
	 *            Process executor factory.
	 */
	public void setProcessExecutorFactory(final ProcessExecutorFactoryInterface processExecutorFactory) {
		factory = processExecutorFactory;
	}

}
