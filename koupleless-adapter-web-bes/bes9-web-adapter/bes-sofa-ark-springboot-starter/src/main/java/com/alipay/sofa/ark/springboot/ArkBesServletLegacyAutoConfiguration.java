/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.ark.springboot;

import com.alipay.sofa.ark.springboot.condition.ConditionalOnArkEnabled;
import com.alipay.sofa.ark.springboot.web.ArkBesServletWebServerFactory;
import com.bes.enterprise.springboot.autoconfigure.BesServletWebServerFactoryConfiguration;
import com.bes.enterprise.springboot.embedded.BesServletWebServerFactory;
import com.bes.enterprise.web.Embedded;
import com.bes.enterprise.web.crane.UpgradeProtocol;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;

/**
 * adapt to springboot 2.1.9.RELEASE
 * @author gaosaroma
 * @since 2.2.8
 */
@Configuration
@ConditionalOnClass(BesServletWebServerFactoryConfiguration.class)
@ConditionalOnArkEnabled
@AutoConfigureBefore(BesServletWebServerFactoryConfiguration.class)
public class ArkBesServletLegacyAutoConfiguration {

    @Configuration
    @ConditionalOnClass(value = { Servlet.class, Embedded.class, UpgradeProtocol.class,
            BesServletWebServerFactory.class }, name = { "com.alipay.sofa.ark.web.embed.bes.ArkBesEmbeddedWebappClassLoader" })
    @ConditionalOnMissingBean(value = BesServletWebServerFactory.class, search = SearchStrategy.CURRENT)
    public static class EmbeddedArkBes {

        @Bean
        @ConditionalOnMissingBean(ArkBesServletWebServerFactory.class)
        public BesServletWebServerFactory besServletWebServerFactory() {
            return new ArkBesServletWebServerFactory();
        }
    }
}