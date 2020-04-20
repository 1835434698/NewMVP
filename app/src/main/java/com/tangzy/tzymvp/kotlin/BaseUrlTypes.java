package com.tangzy.tzymvp.kotlin;

import androidx.annotation.StringDef;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 定义所有的BaseUrl类型, 类型的字段必须添加{@link TypeDef}注解，
 * 属性的类型必须为String类型
 * Create by xianxueliang on 2018/5/4
 */
public interface BaseUrlTypes {

    /** 唯医接口BaseUrl */
    @TypeDef
    String BASE_URL_ALLIN = "BASE_URL_ALLIN";

    /** 基础数据微服务接口BaseUrl */
    @TypeDef
    String BASE_URL_MICRO_SERVICE_COMMON_DATA = "BASE_URL_MICRO_SERVICE_COMMON_DATA";

    /** 日志上传接口BaseUrl */
    @TypeDef
    String BASE_URL_LOG_UPLOAD = "BASE_URL_LOG_UPLOAD";

    /** 基础微服务接口BaseUrl */
    @TypeDef
    String BASE_MICRO_SERVICE_URL = "BASE_MICRO_SERVICE_URL";

    /** OOMSA微服务接口BaseUrl */
    @TypeDef
    String BASE_URL_OOMSA = "BASE_URL_OOMSA";

    /** 基础用户微服务接口BaseUrl */
    @TypeDef
    String BASE_URL_CUSTOMER_MICRO_SERVICE = "BASE_URL_CUSTOMER_MICRO_SERVICE";

    /** 骨科云微服务接口BaseUrl */
    @TypeDef
    String BASE_DOSSIER_MICRO_SERVICE_URL = "BASE_DOSSIER_MICRO_SERVICE_URL";

    /** 骨科云-H5-微服务 */
    @TypeDef
    String BASE_URL_DOSSIER_SERVICE_QA = "BASE_URL_DOSSIER_SERVICE_QA";

    /** 唯医诊疗接口BaseUrl */
    @TypeDef
    String BASE_URL_TOCURE = "BASE_URL_TOCURE";

    /** 唯医诊疗H5接口BaseUrl */
    @TypeDef
    String BASE_URL_TOCURE_H5 = "BASE_URL_TOCURE_H5";

    /** 医栈下载BaseUrl */
    @TypeDef
    String BASE_URL_MEDPLUS_DOWNLOAD = "BASE_URL_MEDPLUS_DOWNLOAD";

    /** 医栈02BaseUrl */
    @TypeDef
    String BASE_URL_MEDPLUS_02 = "BASE_URL_MEDPLUS_02";

    /** 热修复补丁BaseUrl */
    @TypeDef
    String BASE_URL_HOT_FIX_PATCH = "BASE_URL_HOT_FIX_PATCH";

    /** 模块化BaseUrl */
    @TypeDef
    String BASE_URL_MODULATION = "BASE_URL_MODULATION";

    @TypeDef
    String BASE_URL_IM_VIDEO_TRANSFER_CODE_CALLBACK = "BASE_URL_IM_VIDEO_TRANSFER_CODE_CALLBACK";
    /**患者报到*/
    @TypeDef
    String BASE_URL_CHECKIN_PLATFORM = "BASE_URL_CHECKIN_PLATFORM";
    /** 患者管理1.0之后的影像BaseUrl */
    @TypeDef
    String BASE_CARMERA_SERVICE_URL = "BASE_CARMERA_SERVICE_URL";
    /** 患者管理1.0BaseUrl */
    @TypeDef
    String BASE_PATIENT_MANAGER_MICRO_SERVICE_URL = "BASE_PATIENT_MANAGER_MICRO_SERVICE_URL";

  @Retention(RetentionPolicy.SOURCE)
  @StringDef({BASE_URL_ALLIN, BASE_URL_MICRO_SERVICE_COMMON_DATA, BASE_URL_LOG_UPLOAD, BASE_URL_TOCURE,
          BASE_MICRO_SERVICE_URL, BASE_DOSSIER_MICRO_SERVICE_URL, BASE_URL_TOCURE_H5, BASE_URL_CUSTOMER_MICRO_SERVICE,
           BASE_URL_DOSSIER_SERVICE_QA, BASE_URL_MEDPLUS_DOWNLOAD, BASE_URL_MEDPLUS_02,
          BASE_URL_HOT_FIX_PATCH, BASE_URL_OOMSA, BASE_URL_MODULATION, BASE_URL_IM_VIDEO_TRANSFER_CODE_CALLBACK,
          BASE_URL_CHECKIN_PLATFORM,BASE_CARMERA_SERVICE_URL,BASE_PATIENT_MANAGER_MICRO_SERVICE_URL})
  @interface Def {
  }
}
