package com.tangzy.mytest;

import java.util.List;

/**
 * @author tzy
 * @date 2021/6/4 17:53
 * @discription
 */
class FollowupNode<T> {
    String node_id;
    String interval_type;
    String interval;
    String calc_time;
    List<FollowupTask<T>> content_list;
}
