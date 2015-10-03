package org.javamoney.moneta.format;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.format.MonetaryAmountFormat;

import org.javamoney.moneta.function.MonetaryAmountProducer;
import org.javamoney.moneta.function.MoneyProducer;

/**
 * Builder to {@link MonetaryAmountFormat}.
 * @see  {@Link MonetaryAmountDecimalFormatBuilder#newInstance}
 * @see  {@Link MonetaryAmountDecimalFormatBuilder#of}
 * @since 1.0.1
 */
public class MonetaryAmountDecimalFormatBuilder {

    private DecimalFormat decimalFormat;

    private CurrencyUnit currencyUnit;

    private Locale locale;

    private MonetaryAmountProducer producer;

    private MonetaryAmountDecimalFormatBuilder() {
    }

    /**
     * Creates a new instance of {@link MonetaryAmountDecimalFormatBuilder} with default {@link Locale}.
     * @see {@link NumberFormat#getCurrencyInstance()}
     * @return a new instance of {@link MonetaryAmountDecimalFormatBuilder}
     */
    public static MonetaryAmountDecimalFormatBuilder newInstance() {
        MonetaryAmountDecimalFormatBuilder builder = new MonetaryAmountDecimalFormatBuilder();
        builder.decimalFormat = (DecimalFormat) NumberFormat.getCurrencyInstance();
        return builder;
    }

    /**
     * Creates a new instance of {@link MonetaryAmountDecimalFormatBuilder} with {@link Locale} set from parameter.
     * @param locale
     * @see {@link NumberFormat#getCurrencyInstance(Locale)}
     * @return a new instance of {@link MonetaryAmountDecimalFormatBuilder}
     */
    public static MonetaryAmountDecimalFormatBuilder of(Locale locale) {
        MonetaryAmountDecimalFormatBuilder builder = new MonetaryAmountDecimalFormatBuilder();
        builder.decimalFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
        builder.locale = locale;
        return builder;
    }

    /**
     * Creates a new instance of {@link MonetaryAmountDecimalFormatBuilder} with default {@link Locale} and pattern to format the {@link javax.money.MonetaryAmount}.
     * @param  pattern
     * @see {@link DecimalFormat}
     * @see {@link DecimalFormat#DecimalFormat(String)}
     * @return a new instance of {@link MonetaryAmountDecimalFormatBuilder}
     */
    public static MonetaryAmountDecimalFormatBuilder of(String pattern) {
        MonetaryAmountDecimalFormatBuilder builder = new MonetaryAmountDecimalFormatBuilder();
        builder.decimalFormat = new DecimalFormat(pattern);
        return builder;
    }

    /**
     * Creates a new instance of {@link MonetaryAmountDecimalFormatBuilder} with {@link Locale} set from parameter and pattern to format the {@link javax.money.MonetaryAmount}.
     * @param  pattern
     * @param  locale
     * @see {@link DecimalFormat}
     * @see {@link DecimalFormat#DecimalFormat(String)}
     * @return a new instance of {@link MonetaryAmountDecimalFormatBuilder}
     */
    public static MonetaryAmountDecimalFormatBuilder of(String pattern, Locale locale) {
        MonetaryAmountDecimalFormatBuilder builder = new MonetaryAmountDecimalFormatBuilder();
        builder.decimalFormat = new DecimalFormat(pattern, DecimalFormatSymbols.getInstance(locale));
        builder.locale = locale;
        return builder;
    }

    /**
     * Sets the {@link CurrencyUnit}
     * @param currencyUnit
     * @return the {@link MonetaryAmountDecimalFormatBuilder}
     */
    public MonetaryAmountDecimalFormatBuilder withCurrencyUnit(CurrencyUnit currencyUnit) {
        this.currencyUnit = currencyUnit;
        return this;
    }

    /**
     * Sets the {@link MonetaryAmountProducer}
     * @param producer
     * @return the {@link MonetaryAmountDecimalFormatBuilder}
     */
    public MonetaryAmountDecimalFormatBuilder withProducer(MonetaryAmountProducer producer) {
        this.producer = producer;
        return this;
    }

    /**
     * Creates the {@link MonetaryAmountFormat}
     * If @{link Locale} didn't set the default value is {@link Locale#getDefault()}
     * If @{link MonetaryAmountProducer} didn't set the default value is {@link MoneyProducer}
     * If @{link CurrencyUnit} didn't set the default value is a currency from {@link Locale}
     * @return {@link MonetaryAmountFormat}
     */
    public MonetaryAmountFormat build() {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        if (decimalFormat == null) {
            decimalFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
        }
        if (currencyUnit == null) {
            currencyUnit = Monetary.getCurrency(locale);
        }
        if (producer == null) {
            producer = new MoneyProducer();
        }
        decimalFormat.setCurrency(Currency.getInstance(currencyUnit.getCurrencyCode()));
        return new MonetaryAmountDecimalFormat(decimalFormat, producer, currencyUnit);
    }

}
