package com.verdianc.wisiee.Exception.File;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.Exception.BaseException;

public class FileDeleteFailedException extends BaseException {

  public FileDeleteFailedException(Long fileId, String reason) {
    super(ErrorCode.FILE_DELETE_FAILED, "File ID=" + fileId + " 삭제 실패: " + reason);
  }

}
