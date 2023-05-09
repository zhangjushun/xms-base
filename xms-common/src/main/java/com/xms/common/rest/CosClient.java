package com.xms.common.rest;

import ch.qos.logback.core.util.FileUtil;
import com.xms.common.utils.util.FileNameUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.*;
import com.qcloud.cos.endpoint.EndpointBuilder;
import com.qcloud.cos.endpoint.RegionEndpointBuilder;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.exception.Throwables;
import com.qcloud.cos.exception.CosServiceException.ErrorType;
import com.qcloud.cos.http.CosHttpClient;
import com.qcloud.cos.http.CosHttpRequest;
import com.qcloud.cos.http.DefaultCosHttpClient;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpResponseHandler;
import com.qcloud.cos.internal.BucketNameUtils;
import com.qcloud.cos.internal.COSObjectResponseHandler;
import com.qcloud.cos.internal.COSStringResponseHandler;
import com.qcloud.cos.internal.COSVersionHeaderHandler;
import com.qcloud.cos.internal.COSXmlResponseHandler;
import com.qcloud.cos.internal.CosMetadataResponseHandler;
import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.internal.CosServiceResponse;
import com.qcloud.cos.internal.DeleteObjectsResponse;
import com.qcloud.cos.internal.DigestValidationInputStream;
import com.qcloud.cos.internal.HeaderHandler;
import com.qcloud.cos.internal.InputSubstream;
import com.qcloud.cos.internal.LengthCheckInputStream;
import com.qcloud.cos.internal.MD5DigestCalculatingInputStream;
import com.qcloud.cos.internal.MultiObjectDeleteXmlFactory;
import com.qcloud.cos.internal.ObjectExpirationHeaderHandler;
import com.qcloud.cos.internal.ReleasableInputStream;
import com.qcloud.cos.internal.RequestXmlFactory;
import com.qcloud.cos.internal.ResettableInputStream;
import com.qcloud.cos.internal.ResponseHeaderHandlerChain;
import com.qcloud.cos.internal.ServerSideEncryptionHeaderHandler;
import com.qcloud.cos.internal.ServiceClientHolderInputStream;
import com.qcloud.cos.internal.SkipMd5CheckStrategy;
import com.qcloud.cos.internal.Unmarshaller;
import com.qcloud.cos.internal.VIDResultHandler;
import com.qcloud.cos.internal.VoidCosResponseHandler;
import com.qcloud.cos.internal.Unmarshallers.AccessControlListUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.BucketCrossOriginConfigurationUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.BucketDomainConfigurationUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.BucketLifecycleConfigurationUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.BucketLocationUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.BucketLoggingConfigurationnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.BucketReplicationConfigurationUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.BucketTaggingConfigurationUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.BucketVersioningConfigurationUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.BucketWebsiteConfigurationUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.CompleteMultipartUploadResultUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.CopyObjectUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.DeleteBucketInventoryConfigurationUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.DeleteObjectsResultUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.GetBucketInventoryConfigurationUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.InitiateMultipartUploadResultUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.ListBucketInventoryConfigurationsUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.ListBucketsUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.ListMultipartUploadsResultUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.ListObjectsUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.ListPartsResultUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.SetBucketInventoryConfigurationUnmarshaller;
import com.qcloud.cos.internal.Unmarshallers.VersionListUnmarshaller;
import com.qcloud.cos.internal.XmlResponsesSaxParser.CompleteMultipartUploadHandler;
import com.qcloud.cos.internal.XmlResponsesSaxParser.CopyObjectResultHandler;
import com.qcloud.cos.model.AbortMultipartUploadRequest;
import com.qcloud.cos.model.AccessControlList;
import com.qcloud.cos.model.AclXmlFactory;
import com.qcloud.cos.model.AppendObjectRequest;
import com.qcloud.cos.model.AppendObjectResult;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.BucketConfigurationXmlFactory;
import com.qcloud.cos.model.BucketCrossOriginConfiguration;
import com.qcloud.cos.model.BucketDomainConfiguration;
import com.qcloud.cos.model.BucketLifecycleConfiguration;
import com.qcloud.cos.model.BucketLoggingConfiguration;
import com.qcloud.cos.model.BucketPolicy;
import com.qcloud.cos.model.BucketReplicationConfiguration;
import com.qcloud.cos.model.BucketTaggingConfiguration;
import com.qcloud.cos.model.BucketVersioningConfiguration;
import com.qcloud.cos.model.BucketWebsiteConfiguration;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.CompleteMultipartUploadRequest;
import com.qcloud.cos.model.CompleteMultipartUploadResult;
import com.qcloud.cos.model.CopyObjectRequest;
import com.qcloud.cos.model.CopyObjectResult;
import com.qcloud.cos.model.CopyPartRequest;
import com.qcloud.cos.model.CopyPartResult;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.model.DeleteBucketCrossOriginConfigurationRequest;
import com.qcloud.cos.model.DeleteBucketInventoryConfigurationRequest;
import com.qcloud.cos.model.DeleteBucketInventoryConfigurationResult;
import com.qcloud.cos.model.DeleteBucketLifecycleConfigurationRequest;
import com.qcloud.cos.model.DeleteBucketPolicyRequest;
import com.qcloud.cos.model.DeleteBucketReplicationConfigurationRequest;
import com.qcloud.cos.model.DeleteBucketRequest;
import com.qcloud.cos.model.DeleteBucketTaggingConfigurationRequest;
import com.qcloud.cos.model.DeleteBucketWebsiteConfigurationRequest;
import com.qcloud.cos.model.DeleteObjectRequest;
import com.qcloud.cos.model.DeleteObjectsRequest;
import com.qcloud.cos.model.DeleteObjectsResult;
import com.qcloud.cos.model.DeleteVersionRequest;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.GenericBucketRequest;
import com.qcloud.cos.model.GetBucketAclRequest;
import com.qcloud.cos.model.GetBucketCrossOriginConfigurationRequest;
import com.qcloud.cos.model.GetBucketDomainConfigurationRequest;
import com.qcloud.cos.model.GetBucketInventoryConfigurationRequest;
import com.qcloud.cos.model.GetBucketInventoryConfigurationResult;
import com.qcloud.cos.model.GetBucketLifecycleConfigurationRequest;
import com.qcloud.cos.model.GetBucketLocationRequest;
import com.qcloud.cos.model.GetBucketLoggingConfigurationRequest;
import com.qcloud.cos.model.GetBucketPolicyRequest;
import com.qcloud.cos.model.GetBucketReplicationConfigurationRequest;
import com.qcloud.cos.model.GetBucketTaggingConfigurationRequest;
import com.qcloud.cos.model.GetBucketVersioningConfigurationRequest;
import com.qcloud.cos.model.GetBucketWebsiteConfigurationRequest;
import com.qcloud.cos.model.GetObjectAclRequest;
import com.qcloud.cos.model.GetObjectMetadataRequest;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.Grant;
import com.qcloud.cos.model.Grantee;
import com.qcloud.cos.model.HeadBucketRequest;
import com.qcloud.cos.model.HeadBucketResult;
import com.qcloud.cos.model.HeadBucketResultHandler;
import com.qcloud.cos.model.InitiateMultipartUploadRequest;
import com.qcloud.cos.model.InitiateMultipartUploadResult;
import com.qcloud.cos.model.ListBucketInventoryConfigurationsRequest;
import com.qcloud.cos.model.ListBucketInventoryConfigurationsResult;
import com.qcloud.cos.model.ListBucketsRequest;
import com.qcloud.cos.model.ListMultipartUploadsRequest;
import com.qcloud.cos.model.ListNextBatchOfObjectsRequest;
import com.qcloud.cos.model.ListNextBatchOfVersionsRequest;
import com.qcloud.cos.model.ListObjectsRequest;
import com.qcloud.cos.model.ListPartsRequest;
import com.qcloud.cos.model.ListVersionsRequest;
import com.qcloud.cos.model.MultipartUploadListing;
import com.qcloud.cos.model.ObjectListing;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PartListing;
import com.qcloud.cos.model.Permission;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ResponseHeaderOverrides;
import com.qcloud.cos.model.RestoreObjectRequest;
import com.qcloud.cos.model.SSECOSKeyManagementParams;
import com.qcloud.cos.model.SSECustomerKey;
import com.qcloud.cos.model.SetBucketAclRequest;
import com.qcloud.cos.model.SetBucketCrossOriginConfigurationRequest;
import com.qcloud.cos.model.SetBucketDomainConfigurationRequest;
import com.qcloud.cos.model.SetBucketInventoryConfigurationRequest;
import com.qcloud.cos.model.SetBucketInventoryConfigurationResult;
import com.qcloud.cos.model.SetBucketLifecycleConfigurationRequest;
import com.qcloud.cos.model.SetBucketLoggingConfigurationRequest;
import com.qcloud.cos.model.SetBucketPolicyRequest;
import com.qcloud.cos.model.SetBucketReplicationConfigurationRequest;
import com.qcloud.cos.model.SetBucketTaggingConfigurationRequest;
import com.qcloud.cos.model.SetBucketVersioningConfigurationRequest;
import com.qcloud.cos.model.SetBucketWebsiteConfigurationRequest;
import com.qcloud.cos.model.SetObjectAclRequest;
import com.qcloud.cos.model.UploadMode;
import com.qcloud.cos.model.UploadPartRequest;
import com.qcloud.cos.model.UploadPartResult;
import com.qcloud.cos.model.VersionListing;
import com.qcloud.cos.model.CosDataSource.Utils;
import com.qcloud.cos.model.inventory.InventoryConfiguration;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.Base64;
import com.qcloud.cos.utils.BinaryUtils;
import com.qcloud.cos.utils.DateUtils;
import com.qcloud.cos.utils.Md5Utils;
import com.qcloud.cos.utils.ServiceUtils;
import com.qcloud.cos.utils.StringUtils;
import com.qcloud.cos.utils.UrlEncoderUtils;
import com.qcloud.cos.utils.ServiceUtils.RetryableCOSDownloadTask;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import org.apache.commons.codec.DecoderException;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CosClient {
    private COSClient cosClient;

    public CosClient(String accessKeyId, String accessKeySecret, String endpoint) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(accessKeyId, accessKeySecret);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者接口文档 FAQ 中说明。
        ClientConfig clientConfig = new ClientConfig(new Region(endpoint));
        // 3 生成 cos 客户端。
        cosClient = new COSClient(cred, clientConfig);
    }
    public String generatePresignedUrl(String bucketname, String key, String fileName) {
        GeneratePresignedUrlRequest req =
                new GeneratePresignedUrlRequest(bucketname, key, HttpMethodName.GET);
        String suffix = FileNameUtil.extractFileExt1(key);
        Date expirationDate = new Date(System.currentTimeMillis() + 30L * 60L * 1000L);
        ResponseHeaderOverrides response = new ResponseHeaderOverrides();
        response.setContentDisposition("attachment;filename=\""+fileName+suffix+"\"");
        req.setExpiration(expirationDate);
        req.setResponseHeaders(response);
        URL downloadUrl = cosClient.generatePresignedUrl(req);
        String downloadUrlStr = downloadUrl.toString();
        downloadUrlStr = downloadUrlStr.replaceAll("http:", "https:");
        System.out.println(downloadUrlStr);
        return downloadUrlStr;
    }
}
