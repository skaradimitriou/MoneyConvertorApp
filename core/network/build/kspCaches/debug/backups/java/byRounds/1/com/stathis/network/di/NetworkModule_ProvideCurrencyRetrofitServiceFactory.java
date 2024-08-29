package com.stathis.network.di;

import com.stathis.network.service.CurrencyService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class NetworkModule_ProvideCurrencyRetrofitServiceFactory implements Factory<CurrencyService> {
  private final Provider<OkHttpClient> clientProvider;

  public NetworkModule_ProvideCurrencyRetrofitServiceFactory(
      Provider<OkHttpClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public CurrencyService get() {
    return provideCurrencyRetrofitService(clientProvider.get());
  }

  public static NetworkModule_ProvideCurrencyRetrofitServiceFactory create(
      Provider<OkHttpClient> clientProvider) {
    return new NetworkModule_ProvideCurrencyRetrofitServiceFactory(clientProvider);
  }

  public static CurrencyService provideCurrencyRetrofitService(OkHttpClient client) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideCurrencyRetrofitService(client));
  }
}
