/*
 *     Copyright (C) 2016 psygate (https://github.com/psygate)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 */

package com.psygate.minecraft.spigot.sovereignty.banda.data;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created by psygate (https://github.com/psygate) on 04.04.2016.
 */
public class IPRange4 {

    private final byte[] ip;
    private final byte[] mask;

    public IPRange4(byte[] ip, byte[] mask) {
        this.ip = bitwiseAnd(ip, mask);
        this.mask = mask;
    }

    public IPRange4(String spec) throws UnknownHostException {
        if (spec.contains("-")) {
            mask = InetAddress.getByName(spec.split("-")[1]).getAddress();
            ip = bitwiseAnd(InetAddress.getByName(spec.split("-")[0]).getAddress(), mask);
        } else if (spec.contains("/")) {
            mask = hyphenPush(spec.split("/")[1]);
            ip = bitwiseAnd(InetAddress.getByName(spec.split("/")[0]).getAddress(), mask);
        } else {
            throw new IllegalArgumentException("Unknown notation: " + spec);
        }
    }

    public byte[] getIp() {
        return ip;
    }

    public byte[] getMask() {
        return mask;
    }

    public boolean contains(byte[] ip) {
        return Arrays.equals(this.ip, bitwiseAnd(ip, mask));
    }

    private byte[] bitwiseAnd(byte[] ip, byte[] mask) {
        byte[] out = new byte[ip.length];
        for (int i = 0; i < ip.length; i++) {
            out[i] = (byte) ((ip[i] & 0xFF) & (mask[i] & 0xFF));
        }

        return out;
    }

    private byte[] hyphenPush(String s) {
        int size = Integer.parseInt(s) + 1;
        int out = 0;
        for (int i = 0; i < 32; i++) {
            if (i < size) {
                out |= 1;
            }

            out = out << 1;
        }

        return new byte[]{(byte) ((out >>> 24) & 0xFF), (byte) ((out >>> 16) & 0xFF), (byte) ((out >>> 8) & 0xFF), (byte) (out & 0xFF)};
    }

    @Override
    public String toString() {
        return toIPString(ip) + "/" + toIPString(mask);
    }

    private String toIPString(byte[] mask) {
        String out = "";

        for (int i = 0; i < mask.length; i++) {
            out += (int) (mask[i] & 0xFF);
            if (i < mask.length - 1) {
                out += ".";
            }
        }

        return out;
    }
}
