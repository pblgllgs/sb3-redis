package com.pblgllgs.productservice.response;
/*
 *
 * @author pblgl
 * Created on 18-12-2023
 *
 */

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendlyMessage {

    private String title;
    private String description;
    private String buttonPositive;
}
