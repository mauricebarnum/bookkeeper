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
 * NativeIOImpl.
 */
public class NativeIOImpl implements NativeIO {
    private static class CLib {
        static {
            Native.register("c");
        }
        public static native int open(String pathname, int flags, int mode) throws LastErrorException;
        public static native int fsync(int fd) throws LastErrorException;
        public static native int pwrite(int fd, Pointer buf, int count, long offset) throws LastErrorException;
        public static native int posix_memalign(PointerByReference memptr,
                                                int alignment, int size) throws LastErrorException;
        public static native void free(Pointer buf);
        public static native long lseek(int fd, long offset, int whence) throws LastErrorException;
        public static native long pread(int fd, Pointer buf, long size, long offset) throws LastErrorException;
        public static native int close(int fd) throws LastErrorException;
    }

    private static class CLibLinux {
        static {
            Native.register("c");
        }

        public static native int fallocate(int fd, int mode, long offset, long len) throws LastErrorException;
    }

    @Override
    public int open(String pathname, int flags, int mode) throws LastErrorException {
        return CLib.open(pathname, flags, mode);
    }

    @Override
    public int fsync(int fd) {
        return CLib.fsync(fd);
    }

    @Override
    public int pwrite(int fd, Pointer buf, int count, long offset) throws LastErrorException {
        return CLib.pwrite(fd, buf, count, offset);
    }

    @Override
    public int posix_memalign(PointerByReference memptr, int alignment, int size) throws LastErrorException {
        return CLib.posix_memalign(memptr, alignment, size);
    }

    @Override
    public void free(Pointer buf) {
        CLib.free(buf);
    }

    @Override
    public long lseek(int fd, long offset, int whence) throws LastErrorException {
        return CLib.lseek(fd, offset, whence);
    }

    @Override
    public long pread(int fd, Pointer buf, long size, long offset) throws LastErrorException {
        return CLib.pread(fd, buf, size, offset);
    }

    @Override
    public int close(int fd) throws LastErrorException {
        return CLib.close(fd);
    }

    @Override
    public int fallocate(int fd, int mode, long offset, long len) throws LastErrorException, UnsatisfiedLinkError {
        return CLibLinux.fallocate(fd, mode, offset, len);
    }
}
