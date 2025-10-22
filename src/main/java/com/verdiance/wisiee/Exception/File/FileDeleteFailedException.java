package com.verdiance.wisiee.Exception.File;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class FileDeleteFailedException extends BaseException {

  public FileDeleteFailedException(Long fileId, String reason) {
    super(ErrorCode.FILE_DELETE_FAILED, "File ID=" + fileId + " 삭제 실패: " + reason);
  }

}
