package com.pblgllgs.productservice.exception.exceptions;
/*
 *
 * @author pblgl
 * Created on 18-12-2023
 *
 */

import com.pblgllgs.productservice.enums.Language;
import com.pblgllgs.productservice.exception.utils.FriendlyMessageUtils;
import com.pblgllgs.productservice.exception.enums.IFriendlyMessageCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ProductNotCreatedException extends RuntimeException {
    private final Language language;
    private final IFriendlyMessageCode iFriendlyMessageCode;

    public ProductNotCreatedException(Language language, IFriendlyMessageCode iFriendlyMessageCode,String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language, iFriendlyMessageCode));
        this.language = language;
        this.iFriendlyMessageCode = iFriendlyMessageCode;
        log.error(
                "[ProductNotCreatedException] -> message: {} developer message :{}",
                FriendlyMessageUtils.getFriendlyMessage(language, iFriendlyMessageCode),
                message
        );
    }
}
