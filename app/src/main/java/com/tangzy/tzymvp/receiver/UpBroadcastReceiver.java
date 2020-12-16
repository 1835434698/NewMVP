//package com.tangzy.tzymvp.receiver;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//
//import androidx.annotation.CallSuper;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//
//public abstract class UpBroadcastReceiver extends BroadcastReceiver
//    implements TypeParseResultCallback {
//
//  @CallSuper
//  @Override
//  public void onReceive(Context context, Intent intent) {
//    UpBroadcastHandler.parseIntent(context, intent, this);
//  }
//
//  @Override
//  public void onUpPending(@NonNull Context context, @NonNull Object upId, @Nullable String extra) {}
//
//  @Override
//  public void onUpStart(@NonNull Context context, @NonNull Object upId, @Nullable String extra) {}
//
//  @Override
//  public void onAllUpSuccess(@NonNull Context context, @Nullable String extra) {}
//
//  interface TypeParseResultCallback {
//
//    void onUpPending(@NonNull Context context, @NonNull Object upId, @Nullable String extra);
//
//    void onUpStart(@NonNull Context context, @NonNull Object upId, @Nullable String extra);
//
//    void onUpError(@NonNull Context context, @NonNull Object upId, @Nullable String extra);
//
//    void onUpSuccess(@NonNull Context context, @NonNull Object upId, @Nullable String extra);
//
//    void onAllUpSuccess(@NonNull Context context, @Nullable String extra);
//  }
//}
