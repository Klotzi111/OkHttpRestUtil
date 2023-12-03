package de.klotzi111.util.OkHttpRestUtil.httpclient.interceptors.auth;

import java.util.function.Consumer;

import de.klotzi111.util.OkHttpRestUtil.httpclient.interceptors.RequestParameterApplier;

public abstract class Authentication<SELF extends Authentication<SELF>> implements RequestParameterApplier {
	public final Consumer<SELF> refreshAuthenticationFunction;

	public Authentication(Consumer<SELF> refreshAuthenticationFunction) {
		this.refreshAuthenticationFunction = refreshAuthenticationFunction;
	}
}
