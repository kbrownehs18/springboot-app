package com.ekuy.cinema.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RedirectException extends Exception {
    private String url;
}
