package ru.krupt.yast;

import org.junit.Assert;
import org.junit.Test;
import ru.krupt.yast.exception.InvalidParameterFormat;
import ru.krupt.yast.exception.ParameterIsNullAndNotHaveDefaultValueException;

import java.util.Collections;
import java.util.List;

public class TemplateTest {

    @Test(expected = NullPointerException.class)
    public void testNullTemplate() {
        templateAndRenderedEquals(null);
    }

    @Test
    public void testEmptyTemplate() {
        templateAndRenderedEquals("");
    }

    @Test
    public void testWithoutParameters() {
        templateAndRenderedEquals("Уважаемый клиент!\nУведомляем Вас о пополнении баланса");
    }

    @Test(expected = InvalidParameterFormat.class)
    public void testWithInvalidParameter() {
        templateAndRenderedEquals("Добрый день, ${1! Как твои дела? У меня ${2}");
    }

    @Test(expected = InvalidParameterFormat.class)
    public void testWithInvalidParameter2() {
        templateAndRenderedEquals("Добрый день, ${1'}!");
    }

    @Test(expected = InvalidParameterFormat.class)
    public void testWithInvalidParameter3() {
        templateAndRenderedEquals("Добрый день, ${1?'}!");
    }

    @Test(expected = InvalidParameterFormat.class)
    public void testWithInvalidParameter4() {
        templateAndRenderedEquals("Добрый день, ${'1?''}!");
    }

    @Test
    public void testWithSingleInvalidParameter() {
        templateAndRenderedEquals("Добрый день, ${1!");
    }

    @Test(expected = ParameterIsNullAndNotHaveDefaultValueException.class)
    public void testWithoutDefaultValue() {
        templateAndRenderedEquals("Добрый день, ${1}!");
    }

    @Test
    public void testWithNullDefaultValue() {
        templateAndRenderedEquals("Добрый день, ${1?}!", "Добрый день, !");
    }

    @Test
    public void testWithoutParametersDefaultValue() {
        templateAndRenderedEquals("Добрый день, ${1?'Валера'}!", "Добрый день, Валера!");
        templateAndRenderedEquals("Добрый день, ${'Вася'1'Петя'?'Валера'}!", "Добрый день, Валера!");
        templateAndRenderedEquals("Добрый день, ${'Вася'1'Петя'?'Валера'}", "Добрый день, Валера");
        templateAndRenderedEquals("Добрый день, ${'Вася'1'Петя'?}", "Добрый день, ");
    }

    @Test
    public void testWithParameter() {
        templateAndRenderedEqualsWithParameters("Добрый день, ${1}!", Collections.singletonList("Вася"), "Добрый день, Вася!");
        templateAndRenderedEqualsWithParameters("Добрый день, ${'Король '1}!", Collections.singletonList("Вася"), "Добрый день, Король Вася!");
        templateAndRenderedEqualsWithParameters("Добрый день, ${'Великий '1' Всемогущий'}!", Collections.singletonList("Брюс"), "Добрый день, Великий Брюс Всемогущий!");
        templateAndRenderedEqualsWithParameters("Добрый день, ${'Великий '1' Всемогущий'?}!", Collections.singletonList("Брюс"), "Добрый день, Великий Брюс Всемогущий!");
        templateAndRenderedEqualsWithParameters("Добрый день, ${'Великий '1' Всемогущий'?'Вася'}!", Collections.singletonList("Брюс"), "Добрый день, Великий Брюс Всемогущий!");
        templateAndRenderedEqualsWithParameters("Добрый день, ${'Великий '1' Всемогущий'?'Вася'}!", Collections.singletonList((String) null), "Добрый день, Вася!");
        templateAndRenderedEqualsWithParameters("${'Баланс: '1' руб.\nЧтобы постоянно быть на связи подключите услугу \"Автоплатеж\"'?'Сумма баланса не известна.\nОбратитесь в службу поддержки для уточнения баланса'}",
                Collections.singletonList((String) null),
                "Сумма баланса не известна.\nОбратитесь в службу поддержки для уточнения баланса");
        templateAndRenderedEqualsWithParameters("${'Баланс: '1' руб.\nЧтобы постоянно быть на связи подключите услугу \"Автоплатеж\"'?'Сумма баланса не известна.\nОбратитесь в службу поддержки для уточнения баланса'}",
                Collections.singletonList("57.68"),
                "Баланс: 57.68 руб.\nЧтобы постоянно быть на связи подключите услугу \"Автоплатеж\"");
    }

    @Test(expected = InvalidParameterFormat.class)
    public void testInvalidFormat() {
        templateAndRenderedEqualsWithParameters("Добрый день, ${'Король '1' Всемогущий'?asd}!", Collections.singletonList("Вася"), "Добрый день, Король Вася Всемогущий!");
    }

    private void templateAndRenderedEquals(String source) {
        templateAndRenderedEquals(source, source);
    }

    private void templateAndRenderedEquals(String source, String target) {
        templateAndRenderedEqualsWithParameters(source, null, target);
    }

    private void templateAndRenderedEqualsWithParameters(String source, List<String> parameters, String rendered) {
        Template template = new Template(source);
        Assert.assertEquals(rendered, template.render(parameters));
    }

}
