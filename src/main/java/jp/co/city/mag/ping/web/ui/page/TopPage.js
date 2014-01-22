(function() {
    $(function() { initialize(); });

    function initialize() {
        setBootstrapSwitch();
        $('div.categoriesContainer').on('click', '*', function() {
        	alert(111);
            return false;
        });
    }
})();

function setBootstrapSwitch() {
    $('div.categories input[type="checkbox"]').bootstrapSwitch();
}
