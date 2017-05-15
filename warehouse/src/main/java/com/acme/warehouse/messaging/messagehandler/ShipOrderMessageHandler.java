package com.acme.warehouse.messaging.messagehandler;

import com.acme.warehouse.domain.Order;
import com.acme.warehouse.domain.repository.OrderRepository;
import com.acme.warehouse.messaging.dto.ShipOrderVO;
import com.acme.warehouse.messaging.dto.transformer.OrderTransformer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * {@link MessageHandler} to handle the ShipOrder message.
 *
 * @author ajorritsma
 */
@Component
// TODO Annotate this class with the @MessageHandlerBean with the correct JMS type
// Implement this class with the correct interface
public class ShipOrderMessageHandler implements MessageHandler {
	/**
	 * Logger to log events
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ShipOrderMessageHandler.class);
	/**
	 * ObjectMapper for JSON serialization
	 */
	private ObjectMapper objectMapper = new ObjectMapper();
	/**
	 * Transformer to translate the ShipOrderVO to a Order
	 */
	@Autowired
	private OrderTransformer orderTransformer;
	/**
	 * Repository to store orders
	 */
	@Autowired
	private OrderRepository orderRepository;

	/**
	 * Handle the ShipOrderVO message. The order will be stored
	 * @param message The message to handle
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void handleMessage(String message) {
		try {
			ShipOrderVO shipOrderVO = objectMapper.readerFor(ShipOrderVO.class).readValue(message);

			Order order = orderTransformer.transform(shipOrderVO);

			orderRepository.save(order);

			LOGGER.info("Order for customer {} shipped", shipOrderVO.getCustomerName());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
