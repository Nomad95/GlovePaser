/*--------------------------------------------------------------------------
 *  Copyright 2009 Taro L. Saito
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *--------------------------------------------------------------------------*/
//--------------------------------------
// sqlite-jdbc Project
//
// SQLiteOpenMode.java
// Since: Dec 8, 2009
//
// $URL$ 
// $Author$
//--------------------------------------
package temp.org.sqlite;

/**
 * Database file open modes of SQLite.
 * 
 * See also http://sqlite.org/c3ref/open.html
 * 
 * @author leo
 * 
 */
public enum SQLiteOpenMode {
    READONLY(0x00000001),
    READWRITE(0x00000002),
    CREATE(0x00000004),
    DELETEONCLOSE(0x00000008),
    EXCLUSIVE(0x00000010),
    OPEN_URI(0x00000040),
    OPEN_MEMORY(0x00000080),
    MAIN_DB(0x00000100),
    TEMP_DB(0x00000200),
    TRANSIENT_DB(0x00000400),
    MAIN_JOURNAL(0x00000800),
    TEMP_JOURNAL(0x00001000),
    SUBJOURNAL(0x00002000),
    MASTER_JOURNAL(0x00004000),
    NOMUTEX(0x00008000),
    FULLMUTEX(0x00010000),
    SHAREDCACHE(0x00020000),
    PRIVATECACHE(0x00040000)
    ;

    public final int flag;

    private SQLiteOpenMode(int flag) {
        this.flag = flag;
    }
}
