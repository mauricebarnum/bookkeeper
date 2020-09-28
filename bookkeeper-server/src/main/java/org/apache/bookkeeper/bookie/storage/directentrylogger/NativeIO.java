/**
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
package org.apache.bookkeeper.bookie.storage.directentrylogger;

import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

/**
 * NativeIO.
 */
public class NativeIO {
    static {
        Native.register("c");
    }

    static final int O_CREAT = 0100;
    static final int O_RDONLY = 00;
    static final int O_WRONLY = 01;
    static final int O_TRUNC = 01000;
    static final int O_DIRECT = 040000;
    static final int O_DSYNC = 010000;

    static final int SEEK_SET = 0;
    static final int SEEK_END = 2;

    static final int FALLOC_FL_ZERO_RANGE = 0x10;

    native int open(String pathname, int flags, int mode);
    native int posix_fallocate(int fd, long offset, long len);
    native int fallocate(int fd, int mode, long offset, long len);
    native int fsync(int fd);
    native int pwrite(int fd, Pointer buf, int count, long offset) throws LastErrorException;
    native int posix_memalign(PointerByReference memptr, int alignment, int size) throws LastErrorException;
    native long lseek(int fd, long offset, int whence) throws LastErrorException;
    native long pread(int fd, Pointer buf, long size, long offset);
    native int close(int fd);
}
