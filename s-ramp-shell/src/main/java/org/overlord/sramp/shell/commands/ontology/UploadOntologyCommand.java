/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.overlord.sramp.shell.commands.ontology;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.overlord.sramp.client.SrampAtomApiClient;
import org.overlord.sramp.shell.AbstractShellCommand;
import org.overlord.sramp.shell.util.FileNameCompleter;

/**
 * Uploads an ontology (S-RAMP OWL format) to the s-ramp repository.
 *
 * @author eric.wittmann@redhat.com
 */
public class UploadOntologyCommand extends AbstractShellCommand {

	/**
	 * Constructor.
	 */
	public UploadOntologyCommand() {
	}

	/**
	 * @see org.overlord.sramp.common.shell.ShellCommand#printUsage()
	 */
	@Override
	public void printUsage() {
		print("ontology:upload <pathToOntologyFile>");
	}

	/**
	 * @see org.overlord.sramp.common.shell.ShellCommand#printHelp()
	 */
	@Override
	public void printHelp() {
		print("The 'upload' command uploads a new OWL ontology file to the");
		print("S-RAMP repository.  This makes the classes defined in the OWL");
		print("ontology available for use as classifications on artifacts.");
		print("");
		print("Example usage:");
		print(">  ontology:upload /home/uname/files/regions.owl.xml");
	}

	/**
	 * @see org.overlord.sramp.common.shell.ShellCommand#execute()
	 */
	@Override
	public void execute() throws Exception {
		String filePathArg = this.requiredArgument(0, "Please specify a path to a local ontology file.");

		QName clientVarName = new QName("s-ramp", "client");
		SrampAtomApiClient client = (SrampAtomApiClient) getContext().getVariable(clientVarName);
		if (client == null) {
			print("No S-RAMP repository connection is currently open.");
			return;
		}
		InputStream content = null;
		try {
			File file = new File(filePathArg);
			content = FileUtils.openInputStream(file);
			client.uploadOntology(content);
			print("Successfully uploaded a new ontology to the S-RAMP repository.");
		} catch (Exception e) {
			print("FAILED to upload an artifact.");
			print("\t" + e.getMessage());
			IOUtils.closeQuietly(content);
		} finally {
			IOUtils.closeQuietly(content);
		}
	}

	/**
	 * @see org.overlord.sramp.common.shell.AbstractShellCommand#tabCompletion(java.lang.String, java.util.List)
	 */
	@Override
	public int tabCompletion(String lastArgument, List<CharSequence> candidates) {
		if (lastArgument == null)
			lastArgument = "";
		if (getArguments().isEmpty()) {
			FileNameCompleter delegate = new FileNameCompleter();
			return delegate.complete(lastArgument, lastArgument.length(), candidates);
		}
		return -1;
	}
}
