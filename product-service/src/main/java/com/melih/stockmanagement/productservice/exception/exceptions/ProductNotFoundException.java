package com.melih.stockmanagement.productservice.exception.exceptions;

import com.melih.stockmanagement.productservice.enums.Language;
import com.melih.stockmanagement.productservice.exception.enums.IFriendlyMessageCode;
import com.melih.stockmanagement.productservice.exception.utils.FriendlyMessageUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ProductNotFoundException extends RuntimeException{
    private final Language language;
    private final IFriendlyMessageCode iFriendlyMessageCode;

    public ProductNotFoundException(Language language, IFriendlyMessageCode friendlyMessageCode, String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode));
        this.language = language;
        this.iFriendlyMessageCode = friendlyMessageCode;
        log.error("[ProductNotFoundException] -> message:{} developer message: {}", FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode),message);
    }
}
