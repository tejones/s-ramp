/*
 * Copyright 2011 JBoss Inc
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
package org.overlord.sramp.repository.jcr;

import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Test;
import org.overlord.sramp.common.ArtifactType;
import org.overlord.sramp.common.SrampException;
import org.overlord.sramp.common.ontology.SrampOntology;
import org.s_ramp.xmlns._2010.s_ramp.BaseArtifactEnum;
import org.s_ramp.xmlns._2010.s_ramp.BaseArtifactType;
import org.s_ramp.xmlns._2010.s_ramp.Document;


/**
 * Unit test for JCR persistence of S-RAMP classifications.
 *
 * @author eric.wittmann@redhat.com
 */
public class JCRClassificationPersistenceTest extends AbstractJCRPersistenceTest {

    @Test
    public void testPersistClassifications() throws Exception {
    	SrampOntology ontology = createOntology();

        String artifactFileName = "s-ramp-press-release.pdf";
        InputStream contentStream = this.getClass().getResourceAsStream("/sample-files/core/" + artifactFileName);
        Document document = new Document();
        document.setName(artifactFileName);
        document.setArtifactType(BaseArtifactEnum.DOCUMENT);
        document.getClassifiedBy().add(ontology.findClass("China").getUri().toString());

        BaseArtifactType artifact = persistenceManager.persistArtifact(document, contentStream);
        Assert.assertNotNull(artifact);
        if (log.isDebugEnabled()) {
            persistenceManager.printArtifactGraph(artifact.getUuid(), ArtifactType.Document());
        }

        artifact = persistenceManager.getArtifact(artifact.getUuid(), ArtifactType.Document());

        Assert.assertNotNull(artifact.getClassifiedBy());
        Assert.assertEquals(1, artifact.getClassifiedBy().size());
        Assert.assertEquals("urn:example.org/test2#China", artifact.getClassifiedBy().get(0));
    }

	/**
	 * @throws SrampException
	 */
	private SrampOntology createOntology() throws SrampException {
    	SrampOntology ontology = new SrampOntology();
    	ontology.setBase("urn:example.org/test2");
    	ontology.setLabel("Test Ontology #2");
    	ontology.setComment("This is my second test ontology.");

    	SrampOntology.Class world = createClass(ontology, null, "World", "World", "The entire world");
    	SrampOntology.Class asia = createClass(ontology, world, "Asia", "Asia", null);
    	SrampOntology.Class europe = createClass(ontology, world, "Europe", "Europe", "Two world wars");
    	SrampOntology.Class japan = createClass(ontology, asia, "Japan", "Japan", "Samurai *and* ninja?  Not fair.");
    	SrampOntology.Class china = createClass(ontology, asia, "China", "China", "Gunpowder!");
    	SrampOntology.Class uk = createClass(ontology, europe, "UnitedKingdom", "United Kingdom", "The food could be better");
    	SrampOntology.Class germany = createClass(ontology, europe, "Germany", "Germany", "The fatherland");

    	ontology.getRootClasses().add(world);

    	world.getChildren().add(asia);
    	world.getChildren().add(europe);
    	asia.getChildren().add(japan);
    	asia.getChildren().add(china);
    	europe.getChildren().add(uk);
    	europe.getChildren().add(germany);

    	return persistenceManager.persistOntology(ontology);
	}

	/**
	 * Creates a test class.
	 * @param ontology
	 * @param parent
	 * @param id
	 * @param label
	 * @param comment
	 */
	private SrampOntology.Class createClass(SrampOntology ontology, SrampOntology.Class parent, String id, String label, String comment) {
		SrampOntology.Class rval = ontology.createClass(id);
		rval.setParent(parent);
		rval.setComment(comment);
		rval.setLabel(label);
		return rval;
	}

}
