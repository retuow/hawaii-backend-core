/**
 * Copyright 2014-2018 Q24
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.kahu.hawaii.util.call.sql.response;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.annotation.ThreadSafe;
import org.springframework.jdbc.core.RowMapper;

import io.kahu.hawaii.util.call.Response;
import io.kahu.hawaii.util.call.ResponseHandler;
import io.kahu.hawaii.util.exception.ServerError;
import io.kahu.hawaii.util.exception.ServerException;

@ThreadSafe
public class SetResponseHandler<R extends ResultSet, T> implements ResponseHandler<ResultSet, Set<T>> {
    private RowMapper<T> rowMapper;

    public SetResponseHandler(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }
    @Override
    public void addToResponse(ResultSet resultSet, Response<Set<T>> response) throws ServerException {
        try {
            int i = 0;
            Set<T> set = new HashSet<>();
            while (resultSet.next()) {
                set.add(rowMapper.mapRow(resultSet, i));
                i++;
            }
            response.set(set);
        } catch (SQLException e) {
            throw new ServerException(ServerError.UNEXPECTED_EXCEPTION, e);
        }
    }
}
