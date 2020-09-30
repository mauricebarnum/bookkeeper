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
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

/**
 * NativeIO.
 */
public interface NativeIO {
    int O_CREAT = 0100;
    int O_RDONLY = 00;
    int O_WRONLY = 01;
    int O_TRUNC = 01000;
    int O_DIRECT = 040000;
    int O_DSYNC = 010000;

    int SEEK_SET = 0;
    int SEEK_END = 2;

    int FALLOC_FL_ZERO_RANGE = 0x10;

    int open(String pathname, int flags, int mode) throws LastErrorException;
    int fsync(int fd) throws LastErrorException;
    /**
     * fallocate is a linux-only syscall, so callers must handle the possibility that it does
     * not exist.
     */
    int fallocate(int fd, int mode, long offset, long len) throws LastErrorException, UnsatisfiedLinkError;
    int pwrite(int fd, Pointer buf, int count, long offset) throws LastErrorException;
    int posix_memalign(PointerByReference memptr, int alignment, int size) throws LastErrorException;
    void free(Pointer buf);
    long lseek(int fd, long offset, int whence) throws LastErrorException;
    long pread(int fd, Pointer buf, long size, long offset);
    int close(int fd) throws LastErrorException;
}
