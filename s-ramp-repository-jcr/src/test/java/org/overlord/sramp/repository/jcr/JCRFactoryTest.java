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

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;
import org.overlord.sramp.repository.DerivedArtifacts;
import org.overlord.sramp.repository.DerivedArtifactsFactory;
import org.overlord.sramp.repository.PersistenceFactory;
import org.overlord.sramp.repository.PersistenceManager;
import org.overlord.sramp.repository.jcr.JCRPersistence;

/**
 * @author <a href="mailto:kurt.stam@gmail.com">Kurt Stam</a>
 */
public class JCRFactoryTest {

    @Test
    public void testFindServiceConfig() {
        URL url = this.getClass().getClassLoader().getResource("META-INF/services/org.overlord.sramp.repository.DerivedArtifacts");
        System.out.println("URL=" + url);
        Assert.assertNotNull(url);
    }

    @Test
    public void testPersistenceFactory() throws Exception {
        PersistenceManager persistenceManager = PersistenceFactory.newInstance();
        Assert.assertEquals(JCRPersistence.class, persistenceManager.getClass());
    }

    @Test
    public void testDerivedArtifactsFactory() throws Exception {
        DerivedArtifacts derivedArtifacts = DerivedArtifactsFactory.newInstance();
        Assert.assertEquals(JCRPersistence.class, derivedArtifacts.getClass());
    }


}
