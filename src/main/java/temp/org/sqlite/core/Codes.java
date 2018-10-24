/*
 * Copyright (c) 2007 David Crawshaw <david@zentus.com>
 * 
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */
package temp.org.sqlite.core;

public interface Codes
{

    public static final int SQLITE_OK         =   0;


    public static final int SQLITE_ERROR      =   1;


    public static final int SQLITE_INTERNAL   =   2;


    public static final int SQLITE_PERM       =   3;


    public static final int SQLITE_ABORT      =   4;


    public static final int SQLITE_BUSY       =   5;


    public static final int SQLITE_LOCKED     =   6;


    public static final int SQLITE_NOMEM      =   7;


    public static final int SQLITE_READONLY   =   8;


    public static final int SQLITE_INTERRUPT  =   9;


    public static final int SQLITE_IOERR      =  10;


    public static final int SQLITE_CORRUPT    =  11;


    public static final int SQLITE_NOTFOUND   =  12;


    public static final int SQLITE_FULL       =  13;


    public static final int SQLITE_CANTOPEN   =  14;


    public static final int SQLITE_PROTOCOL   =  15;


    public static final int SQLITE_EMPTY      =  16;


    public static final int SQLITE_SCHEMA     =  17;


    public static final int SQLITE_TOOBIG     =  18;


    public static final int SQLITE_CONSTRAINT =  19;


    public static final int SQLITE_MISMATCH   =  20;


    public static final int SQLITE_MISUSE     =  21;


    public static final int SQLITE_NOLFS      =  22;


    public static final int SQLITE_AUTH       =  23;


    public static final int SQLITE_ROW        =  100;


    public static final int SQLITE_DONE       =  101;


    // types returned by sqlite3_column_type()

    public static final int SQLITE_INTEGER    =  1;
    public static final int SQLITE_FLOAT      =  2;
    public static final int SQLITE_TEXT       =  3;
    public static final int SQLITE_BLOB       =  4;
    public static final int SQLITE_NULL       =  5;
}
