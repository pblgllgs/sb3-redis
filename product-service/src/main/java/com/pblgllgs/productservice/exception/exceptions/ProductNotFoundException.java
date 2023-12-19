package com.pblgllgs.productservice.exception.exceptions;

import com.pblgllgs.productservice.enums.Language;
import com.pblgllgs.productservice.exception.enums.IFriendlyMessageCode;
import com.pblgllgs.productservice.exception.utils.FriendlyMessageUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/*
 *
 * @author pblgl
 * Created on 18-12-2023
 *
 */
@Slf4j
@Getter
public class ProductNotFoundException extends RuntimeException {

    private final Language language;
    private final IFriendlyMessageCode iFriendlyMessageCode;

    public ProductNotFoundException(Language language, IFriendlyMessageCode iFriendlyMessageCode, String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language, iFriendlyMessageCode));
        this.language = language;
        this.iFriendlyMessageCode = iFriendlyMessageCode;
        log.error(
                "[ProductNotFoundException] -> message: {} developer message :{}",
                FriendlyMessageUtils.getFriendlyMessage(language, iFriendlyMessageCode),
                message
        );
    }
}
