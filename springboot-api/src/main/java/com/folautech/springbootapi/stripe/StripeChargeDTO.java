package com.folautech.springbootapi.stripe;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class StripeChargeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String            id;
    private String            type;

}
