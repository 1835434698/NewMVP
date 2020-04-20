@file:JvmName("BaseUrlManager")

package com.tangzy.tzymvp

import com.tangzy.tzymvp.kotlin.AllinEnv
import com.tangzy.tzymvp.kotlin.AllinEnv.*
import com.tangzy.tzymvp.kotlin.BaseUrlTypes
import com.tangzy.tzymvp.kotlin.BaseUrlTypes.*
import com.tangzy.tzymvp.util.Logger
import com.tangzy.tzymvp.util.Utils


/**
 * 初始化所有的BaseUrl配置
 */
fun init() {
    Utils.init(
        BaseUrlTypes::class.java, // BaseUrl 类型定义
        AllinEnv::class.java, // Api 环境定义
        fun(baseUrlType: String, apiEnv: Int): String? {
            Logger.d("tangzy", "baseUrlType = "+baseUrlType +"; apiEnv "+apiEnv)
            // 根据[baseUrlType] 和 [env] 获取对应的baseUrl
            fun throwIfEnvInvalid(): Nothing =
                throw IllegalArgumentException("Network framework not support apiEnv of $apiEnv")

            return when (baseUrlType) {
                BASE_URL_ALLIN -> when (apiEnv) {
                    ENV_ONLINE -> "https://androidapi1.allinmd.cn:18081/services/"
                    ENV_OFFLINE -> "http://dev-api.allinmd.cn:18080/services/"
                    ENV_DETEST -> "http://test-api.allinmd.cn:8080/services/"
                    ENV_PRE_ONLINE -> "http://preapi.allinmd.cn:18080/services/"
                    else -> throwIfEnvInvalid()
                }
                BASE_URL_MICRO_SERVICE_COMMON_DATA -> when (apiEnv) {
                    ENV_ONLINE -> "https://omsa.allinmd.cn:16001/base-comm-platform/"
                    ENV_OFFLINE -> "http://dev-gateway.allinmd.cn:16000/base-comm-platform/"
                    ENV_DETEST -> "http://test-gateway.allinmd.cn:16000/base-comm-platform/"
                    ENV_PRE_ONLINE -> "http://premsa.allinmd.cn:16000/base-comm-platform/"
                    else -> throwIfEnvInvalid()
                }
                BASE_URL_LOG_UPLOAD -> when (apiEnv) {
                    ENV_ONLINE -> "http://hfix.allinmd.cn:9021/api/"
                    ENV_OFFLINE -> "http://10.0.1.154:8080/api/"
                    ENV_DETEST -> "http://10.0.1.154:8080/api/"
                    ENV_PRE_ONLINE -> "http://hfix.allinmd.cn:9021/api/"
                    else -> throwIfEnvInvalid()
                }
                BASE_MICRO_SERVICE_URL -> when (apiEnv) {
                    ENV_ONLINE -> "https://omsa.allinmd.cn:16001/"
                    ENV_OFFLINE -> "http://dev-gateway.allinmd.cn:16000/"
                    ENV_DETEST -> "http://test-gateway.allinmd.cn:16000/"
                    ENV_PRE_ONLINE -> "http://premsa.allinmd.cn:16000/"
                    else -> throwIfEnvInvalid()
                }
                BASE_DOSSIER_MICRO_SERVICE_URL -> when (apiEnv) {
                    ENV_ONLINE -> "https://api-gateway.allinmd.cn/dossier-server/"
                    ENV_OFFLINE -> "http://dev-gateway.allinmd.cn/dossier-server/"
                    ENV_DETEST -> "http://test-gateway.allinmd.cn/dossier-server/"
                    ENV_PRE_ONLINE -> " http://pre-gateway.allinmd.cn:16000/dossier-server/"
                    else -> throwIfEnvInvalid()
                }
                BASE_URL_TOCURE -> when (apiEnv) {
                    ENV_ONLINE -> "https://orthoapi1.allinmed.cn:18081/services/"
                    ENV_OFFLINE -> "http://dev-api.allinmed.cn:18080/services/"
                    ENV_DETEST -> "http://test-api.allinmed.cn:18080/services/"
                    ENV_PRE_ONLINE -> "https://orthoapi1.allinmed.cn:18081/services/"
                    else -> throwIfEnvInvalid()
                }
                BASE_URL_TOCURE_H5 -> when (apiEnv) {
                    ENV_ONLINE -> "https://m.allinmed.cn/"
                    ENV_OFFLINE -> "https://m1.allinmed.cn/"
                    ENV_DETEST -> "https://m1.allinmed.cn/"
                    ENV_PRE_ONLINE -> "https://m.allinmed.cn/"
                    else -> throwIfEnvInvalid()
                }
                BASE_URL_CUSTOMER_MICRO_SERVICE -> when (apiEnv) {
                    ENV_ONLINE -> "https://omsa.allinmd.cn:16001/base-customer-platform/"
                    ENV_OFFLINE -> {
                        //骨科云与唯医的用户微服务不一样
                        @Suppress("ConstantConditionIf")
                        if (false) "http://192.168.1.51:39090/base-customer-gkcloud-platform/"
                        else "http://dev-gateway.allinmd.cn:16000/base-customer-platform/"
                    }
                    ENV_DETEST -> "http://test-gateway.allinmd.cn:16000/base-customer-platform/"
                    ENV_PRE_ONLINE -> "http://premsa.allinmd.cn:16000/base-customer-platform/"
                    else -> throwIfEnvInvalid()
                }
                BASE_URL_DOSSIER_SERVICE_QA -> when (apiEnv) {
                    ENV_ONLINE -> "https://mdossier.allinmd.cn/"
                    ENV_OFFLINE -> "https://qa-mdossier.allinmd.cn/"
                    ENV_DETEST -> "https://qa-mdossier.allinmd.cn/"
                    ENV_PRE_ONLINE -> "https://mdossier.allinmd.cn/"
                    else -> throwIfEnvInvalid()
                }
                BASE_URL_MEDPLUS_DOWNLOAD -> when (apiEnv) {
                    ENV_ONLINE -> "http://a.app.qq.com/o/simple.jsp?pkgname=net.medplus.social"
                    ENV_OFFLINE -> "http://dev-d.medical-plus.cn:18181/app/Medplus_v2.3.1_20171024_test_signed_7zip_aligned.apk"
                    ENV_DETEST -> "http://192.168.1.72:18181/app/Medplus_v2.3.1_20171024_test_signed_7zip_aligned.apk"
                    ENV_PRE_ONLINE -> "http://a.app.qq.com/o/simple.jsp?pkgname=net.medplus.social"
                    else -> throwIfEnvInvalid()
                }
                BASE_URL_MEDPLUS_02 -> when (apiEnv) {
                    ENV_ONLINE -> "https://androidapi.medical-plus.cn:18081/services/"
                    ENV_OFFLINE -> "http://dev-api.medical-plus.cn:18080/services/"
                    ENV_DETEST -> "http://192.168.1.198:18080/services/"
                    ENV_PRE_ONLINE -> "https://androidapi.medical-plus.cn:18081/services/"
                    else -> throwIfEnvInvalid()
                }
                BASE_URL_HOT_FIX_PATCH -> when (apiEnv) {
                    ENV_ONLINE -> "http://hfix.allinmd.cn:9011/hotfix-apis/"
                    ENV_OFFLINE -> "http://dev-hotfix.allinmd.cn:9011/hotfix-apis/"
                    ENV_DETEST -> "http://dev-hotfix.allinmd.cn:9011/hotfix-apis/"
                    ENV_PRE_ONLINE -> "http://hfix.allinmd.cn:9011/hotfix-apis/"
                    else -> throwIfEnvInvalid()
                }
                BASE_URL_OOMSA -> when (apiEnv) {
                    ENV_ONLINE -> "https://omsa.allinmd.cn/"
                    ENV_OFFLINE -> "http://dev-gateway.allinmd.cn/"
                    ENV_DETEST -> "http://test-gateway.allinmd.cn/"
                    ENV_PRE_ONLINE -> "https://pre-gateway.allinmd.cn/"
                    else -> throwIfEnvInvalid()
                }
                BASE_URL_MODULATION -> when (apiEnv) {
                    ENV_ONLINE -> "https://api-gateway.allinmd.cn/allinmd-template-platform/"
                    ENV_OFFLINE -> "http://dev-gateway.allinmd.cn/allinmd-template-platform/"
                    ENV_DETEST -> "http://test-gateway.allinmd.cn/allinmd-template-platform/"
                    ENV_PRE_ONLINE -> "http://pre-gateway.allinmd.cn:16000/allinmd-template-platform/"
                    else -> throwIfEnvInvalid()
                }
                BASE_URL_IM_VIDEO_TRANSFER_CODE_CALLBACK -> when (apiEnv) {
                    /*
                                copy from IOS
                                if([WYConfig isOnline]) {
                                    return @"https://api-gateway.allinmd.cn/";
                                } else if([WYConfig isPreOnline]) {
                                    return @"https://pre-gateway.allinmd.cn/";
                                } else if([WYConfig isTest]) {
                                    return @"https://test-gateway.allinmd.cn/";
                                } else {
                                    return @"https://dev-gateway.allinmd.cn/";
                                }
                             */
                    ENV_ONLINE -> "https://api-gateway.allinmd.cn/"
                    ENV_OFFLINE -> "https://dev-gateway.allinmd.cn/"
                    ENV_DETEST -> "https://test-gateway.allinmd.cn/"
                    ENV_PRE_ONLINE -> "https://pre-gateway.allinmd.cn/"
                    else -> throwIfEnvInvalid()
                }
                BASE_URL_CHECKIN_PLATFORM -> when (apiEnv) {

                    ENV_OFFLINE -> "http://dev-gateway.allinmd.cn/allinmd-checkin-platform/"
                    ENV_DETEST -> "http://test-gateway.allinmd.cn/allinmd-checkin-platform/"
                    ENV_PRE_ONLINE -> "http://pre-gateway.allinmd.cn/allinmd-checkin-platform/"
                    ENV_ONLINE -> "https://api-gateway.allinmd.cn/allinmd-checkin-platform/"
                    else -> throwIfEnvInvalid()
                }
                BASE_CARMERA_SERVICE_URL -> when (apiEnv) {
                    ENV_ONLINE -> "https://api-gateway.allinmd.cn/bone-medical-camera/"
                    ENV_OFFLINE -> "http://dev-gateway.allinmd.cn/bone-medical-camera/"
                    ENV_DETEST -> "http://test-gateway.allinmd.cn/bone-medical-camera/"
                    ENV_PRE_ONLINE -> "http://pre-gateway.allinmd.cn/bone-medical-camera/"
                    else -> throwIfEnvInvalid()
                }
                BASE_PATIENT_MANAGER_MICRO_SERVICE_URL -> when (apiEnv) {
                    ENV_ONLINE -> "https://api-gateway.allinmd.cn/patient-manager/"
                    ENV_OFFLINE -> "http://dev-gateway.allinmd.cn/patient-manager/"
                    ENV_DETEST -> "http://test-gateway.allinmd.cn/patient-manager/"
                    ENV_PRE_ONLINE -> " http://pre-gateway.allinmd.cn:16000/patient-manager/"
                    else -> throwIfEnvInvalid()
                }
                else -> throw IllegalArgumentException("Network framework not support baseUrlType of $baseUrlType")
            }
        },
        1 // 设置所有接口类型默认的的运行环境
//        currentApiEnv() // 设置所有接口类型默认的的运行环境
    )
}

///**
// * 获取当前所有接口的运行环境[AllinEnv]
// */
//@AllinEnv.Def
//fun currentApiEnv(): Int = APIEnvManager.getApiEnv()
//
///**
// * 获取指定类型[baseUrlType]的baseUrl
// */
//fun baseUrlOf(@BaseUrlTypes.Def baseUrlType: String): String {
//    return MultipleBaseUrlHelper.baseUrlOf(baseUrlType).orEmpty()
//}
