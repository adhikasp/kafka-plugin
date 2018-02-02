/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blibli.oss.kafka.interceptor;

import com.blibli.oss.kafka.interceptor.events.ConsumerEvent;
import com.blibli.oss.kafka.interceptor.events.ProducerEvent;
import com.blibli.oss.kafka.properties.KafkaProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Eko Kurniawan Khannedy
 */
@Slf4j
public class LogInterceptor implements KafkaConsumerInterceptor, KafkaProducerInterceptor {

  private KafkaProperties.LogProperties logProperties;

  public LogInterceptor(KafkaProperties.LogProperties logProperties) {
    this.logProperties = logProperties;
  }

  @Override
  public void beforeSend(ProducerEvent event) {
    if (logProperties.isBeforeSend()) {
      log.info("Send to topic {} with message {}", event.getTopic(), event.getValue());
    }
  }

  @Override
  public boolean beforeConsume(ConsumerEvent event) {
    if (logProperties.isBeforeConsume()) {
      log.info("Receive from topic {} with message {}", event.getTopic(), event.getValue());
    }
    return false;
  }
}
