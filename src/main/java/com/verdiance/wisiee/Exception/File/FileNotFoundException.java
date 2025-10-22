package com.verdiance.wisiee.Exception.File;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class FileNotFoundException extends BaseException {

  public FileNotFoundException(Long fileId) {
    super(ErrorCode.FILE_NOT_FOUND, "File ID=" + fileId + " 없음");
  }

}
