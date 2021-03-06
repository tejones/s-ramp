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
package org.overlord.sramp.common.derived;

import java.io.IOException;
import java.util.Collection;

import javax.xml.xpath.XPath;

import org.overlord.sramp.common.query.xpath.StaticNamespaceContext;
import org.s_ramp.xmlns._2010.s_ramp.BaseArtifactType;
import org.w3c.dom.Element;

/**
 * Creates derived content from a Policy document.  This will create the derived content as
 * defined in the Policy model found in the s-ramp specification.  The following derived
 * artifact types will (potentially) be created:
 *
 * <ul>
 *   <li>PolicyExpression</li>
 *   <li>PolicyAttachment</li>
 * </ul>
 *
 * @author eric.wittmann@redhat.com
 */
public class PolicyDeriver extends AbstractXmlDeriver {

	/**
	 * Constructor.
	 */
	public PolicyDeriver() {
	}

	/**
	 * @see org.overlord.sramp.common.repository.derived.AbstractXmlDeriver#configureNamespaceMappings(org.overlord.sramp.common.query.xpath.StaticNamespaceContext)
	 */
	@Override
	protected void configureNamespaceMappings(StaticNamespaceContext namespaceContext) {
		super.configureNamespaceMappings(namespaceContext);
		namespaceContext.addMapping("wsp", "http://www.w3.org/ns/ws-policy");
	}

	/**
	 * @see org.overlord.sramp.common.derived.AbstractXmlDeriver#derive(java.util.Collection, org.s_ramp.xmlns._2010.s_ramp.BaseArtifactType, org.w3c.dom.Element, javax.xml.xpath.XPath)
	 */
	@Override
	protected void derive(Collection<BaseArtifactType> derivedArtifacts, BaseArtifactType artifact,
			Element rootElement, XPath xpath) throws IOException {
	}
}
