package com.verdianc.wisiee.Exception.File;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.Exception.BaseException;

public class FileNotFoundException extends BaseException {

  public FileNotFoundException(Long fileId) {
    super(ErrorCode.FILE_NOT_FOUND, "File ID=" + fileId + " 없음");
  }

}
