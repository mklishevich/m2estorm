m2estorm
========

Provides auto completion for next type of calls (depends on configuration file):

- Mage::helper('M2ePro/Component_%channel%')->getModel('%entity%')
- Mage::helper('M2ePro/Component_%channel%')->getObject('%entity%', %id%)
- Mage::helper('M2ePro/Component_%channel%')->getCachedObject('%entity%', %id%)
- Mage::helper('M2ePro/Component_%channel%')->getCollection('%entity%')
- Mage::helper('M2ePro/Component')->getCachedComponentObject('%channel%', '%entity%', %id)

Example of the configuration file:

    <?xml version="1.0" encoding="utf-8"?>
    <config>
        <factories>
            <factory signature="Mage.helper.M2ePro/Component_Ebay.getModel" />
            <factory signature="Mage.helper.M2ePro/Component_Ebay.getObject" />
            <factory signature="Mage.helper.M2ePro/Component_Ebay.getCachedObject" />
            <factory signature="Mage.helper.M2ePro/Component_Ebay.getCollection" />
            <factory signature="Mage.helper.M2ePro/Component_Amazon.getModel" />
            <factory signature="Mage.helper.M2ePro/Component_Amazon.getObject" />
            <factory signature="Mage.helper.M2ePro/Component_Amazon.getCachedObject" />
            <factory signature="Mage.helper.M2ePro/Component_Amazon.getCollection" />
            <factory signature="Mage.helper.M2ePro/Component_Buy.getModel" />
            <factory signature="Mage.helper.M2ePro/Component_Buy.getObject" />
            <factory signature="Mage.helper.M2ePro/Component_Buy.getCachedObject" />
            <factory signature="Mage.helper.M2ePro/Component_Buy.getCollection" />
            <factory signature="Mage.helper.M2ePro/Component_Play.getModel" />
            <factory signature="Mage.helper.M2ePro/Component_Play.getObject" />
            <factory signature="Mage.helper.M2ePro/Component_Play.getCachedObject" />
            <factory signature="Mage.helper.M2ePro/Component_Play.getCollection" />
            <factory signature="Mage.helper.M2ePro/Component.getCachedComponentObject" entity_argument_position="1" />
        </factories>
    </config>