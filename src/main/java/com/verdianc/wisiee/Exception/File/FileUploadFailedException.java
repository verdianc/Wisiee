package com.verdianc.wisiee.Exception.File;


import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.Exception.BaseException;

public class FileUploadFailedException extends BaseException {
  public FileUploadFailedException(String reason) {
    super(ErrorCode.FILE_UPLOAD_FAILED, "업로드 실패: " + reason);
  }
}