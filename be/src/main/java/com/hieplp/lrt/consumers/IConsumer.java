package com.hieplp.lrt.consumers;

public interface IConsumer {
    /**
     * Initialize consumer. Must be called first.
     *
     * @return consumer instance
     */
    IConsumer init();

    /**
     * Configure CORS
     *
     * @return consumer instance
     */
    IConsumer cors();

    /**
     * Configure APIs
     *
     * @return consumer instance
     */
    IConsumer apis();

    /**
     * Configure SSE
     *
     * @return consumer instance
     */
    IConsumer sse();

    /**
     * Start consumer
     */
    void start();
}
