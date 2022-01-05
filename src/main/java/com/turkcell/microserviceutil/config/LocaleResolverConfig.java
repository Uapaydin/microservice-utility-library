package com.turkcell.microserviceutil.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Configuration
public class LocaleResolverConfig extends AcceptHeaderLocaleResolver {

    static final List<Locale> LOCALES = Collections.unmodifiableList(Arrays.asList(new Locale("en"),new Locale("tr")));

    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest){
        String headerLang = httpServletRequest.getHeader("Accept-Language");
        return headerLang == null || headerLang.isEmpty()
                ? Locale.getDefault(): Locale.lookup(Locale.LanguageRange.parse(headerLang),LOCALES);
    }
}
