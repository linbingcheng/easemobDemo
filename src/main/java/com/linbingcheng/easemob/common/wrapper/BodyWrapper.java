package com.linbingcheng.easemob.common.wrapper;

import com.fasterxml.jackson.databind.node.ContainerNode;

public interface BodyWrapper {
    ContainerNode<?> getBody();

    Boolean validate();
}
