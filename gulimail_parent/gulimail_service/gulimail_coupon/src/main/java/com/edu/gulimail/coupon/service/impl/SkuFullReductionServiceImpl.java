package com.edu.gulimail.coupon.service.impl;

import com.edu.common.to.MemberPrice;
import com.edu.common.to.SKuReduceTo;
import com.edu.gulimail.coupon.entity.MemberPriceEntity;
import com.edu.gulimail.coupon.entity.SkuLadderEntity;
import com.edu.gulimail.coupon.service.MemberPriceService;
import com.edu.gulimail.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.common.utils.PageUtils;
import com.edu.common.utils.Query;

import com.edu.gulimail.coupon.dao.SkuFullReductionDao;
import com.edu.gulimail.coupon.entity.SkuFullReductionEntity;
import com.edu.gulimail.coupon.service.SkuFullReductionService;

import javax.annotation.Resource;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Resource
    private SkuLadderService skuLadderService;

    @Resource
    private MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveReductionInfo(SKuReduceTo sKuReduceTo) {
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        BeanUtils.copyProperties(sKuReduceTo, skuLadderEntity);
        skuLadderEntity.setAddOther(sKuReduceTo.getCountStatus());

        // sku信息的保存
        if (skuLadderEntity.getFullCount() > 0){
            skuLadderService.save(skuLadderEntity);
        }
        //  进行sku满减信息的保存：
        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(sKuReduceTo, skuFullReductionEntity);
        // TODO 这没有是否是进行优惠的叠加的设置
        if (skuFullReductionEntity.getFullPrice().compareTo(new BigDecimal("0")) == 1){
            this.save(skuFullReductionEntity);
        }
        // 进行价格的保存
        List<MemberPrice> memberPrice = sKuReduceTo.getMemberPrice();
        List<MemberPriceEntity> collect = memberPrice.stream().filter(memberPrice1 ->
             memberPrice1.getPrice().compareTo(new BigDecimal("0") ) == 1).map(item -> {

            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setSkuId(sKuReduceTo.getSkuId());
            memberPriceEntity.setMemberLevelId(item.getId());
            memberPriceEntity.setMemberLevelName(item.getName());
            memberPriceEntity.setMemberPrice(item.getPrice());
            // TODO 这里设置的是默认都是进行优惠的叠加的
            memberPriceEntity.setAddOther(1);
            return memberPriceEntity;
        }).collect(Collectors.toList());

        memberPriceService.saveBatch(collect);

    }

}