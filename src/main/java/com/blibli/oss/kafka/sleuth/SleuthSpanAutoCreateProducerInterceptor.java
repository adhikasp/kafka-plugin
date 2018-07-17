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

package com.blibli.oss.kafka.sleuth;

import com.blibli.oss.kafka.interceptor.KafkaProducerInterceptor;
import com.blibli.oss.kafka.interceptor.events.ProducerEvent;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.core.Ordered;

/**
 * This KafkaProducerInterceptor will auto create Sleuth Span
 * if there is no span exists in currentSpan
 *
 * @author Eko Kurniawan Khannedy
 */
public class SleuthSpanAutoCreateProducerInterceptor implements KafkaProducerInterceptor {

  private static final String KAFKA_COMPONENT = "kafka:producer";

  private Tracer tracer;

  public SleuthSpanAutoCreateProducerInterceptor(Tracer tracer) {
    this.tracer = tracer;
  }

  @Override
  public void beforeSend(ProducerEvent event) {
    if (tracer.getCurrentSpan() == null) {
      String name = KAFKA_COMPONENT + ":" + event.getTopic();
      tracer.createSpan(name);
    }
  }

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }
}
