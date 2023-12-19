package com.pblgllgs.productcacheservice.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

/*
 *
 * @author pblgl
 * Created on 18-12-2023
 *
 */
@Data
@Builder
public class InternalApiResponse<T> {
    private FriendlyMessage friendlyMessage;
    private T payload;
    private boolean hasError;
    private List<String> errorMessages;
    private HttpStatus httpStatus;
}
