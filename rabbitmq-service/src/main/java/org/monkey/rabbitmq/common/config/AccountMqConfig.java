package org.monkey.rabbitmq.common.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountMqConfig {
    public static final String EXCHANGE_DIRECT_ACCOUNT_MANAGER = "direct_exchange_account_manager";

    public static final String QUEUE_ACCOUNT_MANAGER = "queue.account.manager";

    public static final String KEY_ACCOUNT_MANAGER = "key.account.manager";
}
