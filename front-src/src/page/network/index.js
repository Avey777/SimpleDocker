import {Component} from "react";
import {Button, Space, Input, Table, Tag} from "antd";
import {getNetworkList} from "../../api/NetworkApi";
import _ from 'lodash'

/**
 * 主页布局文件
 */
class NetworkPage extends Component {

    constructor(props) {
        super(props);
        this.state = {networkList: []}
    }


    componentDidMount() {
        getNetworkList().then(resp => {
            let networkList = resp.data
            this.setState({networkList})
        })
    }


    render() {
        const columns = [
            {
                title: '网络ID',
                dataIndex: 'Id',
                key: 'Id',
                render: id => <span>{id.substring(0, 30)}</span>,
                ellipsis: true,
                width: 100,
            },
            {
                title: '网络名称',
                dataIndex: 'Name',
                key: 'Name',
                width: 50,
            },
            {
                title: '网络模式',
                dataIndex: 'Driver',
                key: 'Driver',
                width: 50,
            },
            {
                title: '网关/子网掩码',
                dataIndex: 'IPAM',
                key: 'Id',
                render: ipam => {
                    let configs = _.get(ipam, "Config", []);
                    return configs.map(c => {
                        return <div><Tag color='red'>{c.Gateway}</Tag> <Tag color='blue'>{c.Subnet}</Tag></div>
                    })
                },
                width: 120,
            },
            {
                title: '作用域',
                dataIndex: 'Scope',
                key: 'Scope',
                ellipsis: true,
                width: 30,
            },
            {
                title: '创建时间',
                dataIndex: 'Created',
                key: 'Created',
                width: 120
            },
            {
                title: '操作',
                dataIndex: 'address',
                key: 'address 4',
                fixed: 'right',
                width: 80,
                render: () =>
                    <Space>
                        <Button size="small" type="link">运行</Button>
                        <Button size="small" type="link">删除</Button>
                        <Button size="small" type="link">更多</Button>
                    </Space>

            },
        ];

        return (
            <div>
                <div style={{margin: 10}}>
                    <Input placeholder="请输入过滤词" style={{width: 400}}/>
                </div>
                <Table
                    bordered
                    pagination="bottomCenter"
                    columns={columns}
                    dataSource={this.state.networkList}
                    size="small"/>
            </div>
        )

    }
}

export default NetworkPage;
