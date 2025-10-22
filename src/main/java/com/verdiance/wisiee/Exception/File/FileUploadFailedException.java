package com.verdiance.wisiee.Exception.File;


import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class FileUploadFailedException extends BaseException {
  public FileUploadFailedException(String reason) {
    super(ErrorCode.FILE_UPLOAD_FAILED, "업로드 실패: " + reason);
  }
}