package com.taoes.simpledocker.ws.callback;

import com.github.dockerjava.api.model.Frame;

import java.io.Closeable;

import java.nio.ByteBuffer;
import javax.websocket.Session;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TerminalResultCallback extends AbstractResultCallback<Frame> {


    public TerminalResultCallback(Session session) {
        super("容器终端", session);
    }

    private final static String BANNER_CONTENT =
            "\033[33m   _____ _                 __        ____             __\r\n"
                    + "\033[33m  / ___/(_)___ ___  ____  / /__     / __ \\____  _____/ /_____  _____\r\n"
                    + "\033[33m  \\__ \\/ / __ `__ \\/ __ \\/ / _ \\   / / / / __ \\/ ___/ //_/ _ \\/ ___/\r\n"
                    + "\033[33m ___/ / / / / / / / /_/ / /  __/  / /_/ / /_/ / /__/ ,< /  __/ /    \r\n"
                    + "\033[33m/____/_/_/ /_/ /_/ .___/_/\\___/  /_____/\\____/\\___/_/|_|\\___/_/     \r\n"
                    + "\033[33m                /_/                               \t\t\t\t\t   \r\n\r\n"
                    + "\033[32m 欢迎您使用 SimpleDocker, 容器终端已为您连接成功 ...............  \033[0m \r\n\r\n"
                    + "\033[37m 程序默认使用 \033[32m /bin/sh \033[0m 连接，如有必要请手动切换到 /bin/bash 或者其他 shell 环境  \033[0m \r\n\n"
                    + "\033[37m 如遇BUG或者Issue 欢迎访问 https://github.com/taoes  或者  https://gitee.com/taoes_admin  联系作者  \033[0m \r\n\n";

    @SneakyThrows
    @Override
    public void onStart(Closeable closeable) {
        sendMessage(BANNER_CONTENT);
    }

    @SneakyThrows
    @Override
    public void onNext(Frame object) {
        sendMessage(ByteBuffer.wrap(object.getPayload()));
    }

    @Override
    @SneakyThrows
    public void onError(Throwable throwable) {
        sendMessage("\u001B[31m 出现异常:" + throwable.getMessage() + "\u001B[0m \n");
        this.close();
    }

    @Override
    @SneakyThrows
    public void onComplete() {
        sendMessage("\u001B[31m 终端服务已关闭，如需再次进入请尝试刷新或者在容器管理页进入  \u001B[0m");
        this.close();
    }


}
