//�����Ҫ�����߼�js����
//javascript ģ�黯
//seckill.detail.init(params);
var seckill = {
    //��װ��ɱ���ajax��url
    URL: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (seckillId) {
            return '/seckill/' + seckillId + '/exposer';
        },
        execution: function (seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/execution';
        }
    },
    handleSeckillKill: function (seckillId, node) {
        //������ɱ�߼�
        node.hide()
            .html('<button class="btn btn-primary btn-lg " id = "killBtn">��ʼ��ɱ</button>') //��ť
        $.post(seckill.URL.exposer(seckillId), {}, function (result) {
            //�ڻص������У�ִ�н�������
            if (result && result['success']) {
                var exposer = result['data'];
                if (exposer['exposed']) {
                    //������ɱ
                    //��ȡ��ɱ��ַ
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId, md5);
                    console.log("killUrl" + killUrl);
                    //��һ��click�¼�-->one
                    $('#killBtn').one('click', function () {
                        //ִ����ɱ����
                        //1.�Ƚ��ð�ť
                        $(this).addClass('disabled');
                        //2.������ɱ����
                        $.post(killUrl, {}, function (result) {
                            if (result && result['success']) {
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                //3.��ʾ��ɱ���
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }
                        });
                    });
        node.show();
                } else {
                    //δ������ɱ
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    //���¼����ʱ�߼�
                    seckill.countdown(seckillId, now, start, end);
                }
            } else {
                console.log('result:' + result);
            }
        });
    },
    //��֤�ֻ���
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    countdown: function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        //ʱ����ж�
        if (nowTime > endTime) {
            //��ɱ����
            seckillBox.html('��ɱ������');
        } else if (nowTime < startTime) {
            //��ɱδ��ʼ,��ʱ�¼���
            var killTime = new Date(startTime + 1000); //��ֹ�û���ʱƫ��
            seckillBox.countdown(killTime, function (event) {
                var format = event.strftime('��ɱ��ʱ��%D�� %Hʱ %M�� %S��');
                seckillBox.html(format);
                /*ʱ����ɻص��¼�*/
            }).on('finish.countdown', function () {
                //��ȡ��ɱ��ַ��������ʾ�߼���ִ����ɱ
                seckill.handleSeckillKill(seckillId, seckillBox);
            })
        } else {
            //��ɱ����
            seckill.handleSeckillKill(seckillId, seckillBox);
        }
    },
    //����ҳ��ɱ�߼�
    detail: {
        //����ҳ��ʼ��
        init: function (params) {
            //�ֻ���֤�͵�¼����ʱ����
            //�滮���ǵĽ�������
            //��cookie�в����ֻ���
            var killPhone = $.cookie('killPhone');
            //��֤�ֻ���
            if (!seckill.validatePhone(killPhone)) {
                //��phone
                //�������
                var killPhoneModel = $('#killPhoneModel');
                killPhoneModel.modal({
                    show: true,//��ʾ������
                    backdrop: 'static',//��ֹλ�ùر�
                    keyboard: false//�رռ����¼�
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    console.log('inputPhone=' + inputPhone);//TODO
                    if (seckill.validatePhone(inputPhone)) {
                        //�绰д��cookie
                        $.cookie('killPhone', inputPhone, {expires: 7, path: 'seckill'});
                        //ˢ��ҳ��
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">�ֻ��Ŵ���</label>').show(300);
                    }
                });
            }
            //�Ѿ���¼
            //��ʱ�������
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(seckill.URL.now(), {}, function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];
                    //��ʱ�����ʱ���ж�,��ʱ����
                    seckill.countdown(seckillId, nowTime, startTime, endTime);
                } else {
                    console.log('result:' + result);
                }
            })


        }
    }
}