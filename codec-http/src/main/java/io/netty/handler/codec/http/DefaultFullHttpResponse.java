/*
 * Copyright 2013 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


/**
 * Default implementation of a {@link FullHttpResponse}.
 */
public class DefaultFullHttpResponse extends DefaultHttpResponse implements FullHttpResponse {

    private final ByteBuf content;
    private final HttpHeaders trailingHeaders = new DefaultHttpHeaders();

    public DefaultFullHttpResponse(HttpVersion version, HttpResponseStatus status) {
        this(version, status, Unpooled.buffer(0));
    }

    public DefaultFullHttpResponse(HttpVersion version, HttpResponseStatus status, ByteBuf content) {
        super(version, status);
        if (content == null) {
            throw new NullPointerException("content");
        }
        this.content = content;
    }

    @Override
    public HttpHeaders trailingHeaders() {
        return trailingHeaders;
    }

    @Override
    public ByteBuf data() {
        return content;
    }

    @Override
    public boolean isFreed() {
        return content.isFreed();
    }

    @Override
    public void free() {
        content.free();
    }

    @Override
    public FullHttpResponse setProtocolVersion(HttpVersion version) {
        super.setProtocolVersion(version);
        return this;
    }

    @Override
    public FullHttpResponse setStatus(HttpResponseStatus status) {
        super.setStatus(status);
        return this;
    }

    @Override
    public FullHttpResponse copy() {
        DefaultFullHttpResponse copy = new DefaultFullHttpResponse(getProtocolVersion(), getStatus(), data().copy());
        copy.headers().set(headers());
        copy.trailingHeaders().set(trailingHeaders());
        return copy;
    }
}
